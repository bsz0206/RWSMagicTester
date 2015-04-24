package magictester.rws.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;

import magictester.core.JSON;
import magictester.core.UtilityFunctions;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

public class APIAuth {
	TestManager_RWSAPIv3 RWSAPIv3;
	Client webClient=null;
	SessionToken _sessionToken=null;
	long ServerTimeDiff=0;
	
	public APIAuth(TestManager_RWSAPIv3 currentTestManager) {
		RWSAPIv3=currentTestManager;

	}

	public SessionToken Login() throws Exception {
		if(_sessionToken==null) {
			_sessionToken=(SessionToken) RWSAPIv3.getChiefManager().getSessionProfileValue(
					RWSAPIv3.getTestSessionName(),
					RWSAPIv3.getTestProfileName(),
					"SessionToken"
				);			
		}
		if(_sessionToken==null) {
			_sessionToken=new SessionToken(
					RWSAPIv3.getTestSessionName(),
					RWSAPIv3.getTestProfileName()
				);
		}

		if(!_sessionToken.IsValid()) {
			_sessionToken=DoLogin();
		}

		if(!_sessionToken.IsValid()) {
			_sessionToken=null;
		}

		RWSAPIv3.getChiefManager().setSessionProfileValue(
				RWSAPIv3.getTestSessionName(),
				RWSAPIv3.getTestProfileName(),
				"SessionToken",
				_sessionToken
		);
		
		return _sessionToken;
	}
	

	private SessionToken DoLogin() throws Exception {
		String clientID=RWSAPIv3.getTestSessionParameter("ClientID");

		Date serverTimeUTC=GetServerTime();
		Date clientTimeUTC=UtilityFunctions.getSystemTimeUTC();
		ServerTimeDiff=serverTimeUTC.getTime() - clientTimeUTC.getTime();
		
		String scopeString= GetScopeUrlEncoedStringForAction("login");
		String authenticityToken=GetAuthenticity_Token(clientID, scopeString);
		
		String username=RWSAPIv3.getTestProfileParameter("Username");
		String password=RWSAPIv3.getTestProfileParameter("Password");
		return GetSessionToken(clientID, authenticityToken, username, password);
	}

	private String GetAuthenticity_Token(String clientID, String scopeString) throws IOException {
		String methodAddress=GetAuthMethodURL("oauth/authorize");

		List<SimpleEntry<String, String>> parameters=new ArrayList<SimpleEntry<String, String>>();
		parameters.add(new SimpleEntry<String, String>("client_id", clientID));
		parameters.add(new SimpleEntry<String, String>("redirect_uri", RWSAPIv3.getTestSessionParameter("BaseAuthUrl") + "/_status"));
		parameters.add(new SimpleEntry<String, String>("response_type", "token"));
		parameters.add(new SimpleEntry<String, String>("scope", scopeString));
		parameters.add(new SimpleEntry<String, String>("state", "e30"));
		parameters.add(new SimpleEntry<String, String>("webflow", "true"));
		String response=APIConnector.CallServer_GET(methodAddress, parameters);
		String ret=GetMetaContent(response, "csrf-token");

		return ret;
	}

	private String GetMetaContent(String html, String metaName) {
		int from = html.lastIndexOf(" name=\"csrf-token\"");
		from+=28;
		int to    =html.indexOf("\"", from);
		String tag = html.substring(from, to);
		return tag;
	}

	String GetScopeUrlEncoedStringForAction(String typeOfScope) throws UnsupportedEncodingException {
		String ret=URLEncoder.encode(GetScopeStringForAction(typeOfScope), "UTF-8").replace("+", "%20");
		return ret;
	}

	private Date GetServerTime() throws Exception {
		String methodAddress=GetAPI2MethodURL("server_time");
		String response=APIConnector.CallServer_GET(methodAddress);
		String timeStr=JSON.GetStringByJSONPath(response, "$");
		long epochTime=Long.parseLong(timeStr);
		Date ret=UtilityFunctions.ConvertEpochTimeToUTC(epochTime);
		return ret;
	}
	
	private class PayLoad {
		String content=null;
		
		private PayLoad() {
			content="";
		}
		
		private void AddParam(String name, String value) {
			if(content.length()>0) content+="&";
			content+=name;
			content+="=";
			content+=value;
		}
		
		public String get() {
			return content;
		}
	}

	private SessionToken GetSessionToken(String clientID, String authenticityToken, String username, String password) throws NumberFormatException, Exception {
		String methodAddress=GetAuthMethodURL("oauth/authenticate");

		List<SimpleEntry<String, String>> parameters=new ArrayList<SimpleEntry<String, String>>();
		PayLoad payLoad=new PayLoad();
		payLoad.AddParam("utf8", "%E2%9C%93");
		payLoad.AddParam("authenticity_token", URLEncoder.encode(authenticityToken, "UTF-8"));
		payLoad.AddParam("client_id", clientID);
		payLoad.AddParam("response_type", "token");
		payLoad.AddParam("redirect_uri", 
				URLEncoder.encode((RWSAPIv3.getTestSessionParameter("BaseAuthUrl") + "/_status"), "UTF-8"));
		payLoad.AddParam("scope", URLEncoder.encode(GetScopeStringForAction("sessionLogin"), "UTF-8"));
		payLoad.AddParam("state", "e30");
		payLoad.AddParam("require_account_id", "");
		payLoad.AddParam("username", URLEncoder.encode(username, "UTF-8"));
		payLoad.AddParam("password", URLEncoder.encode(password, "UTF-8"));
		payLoad.AddParam("commit", "Sign+In");

		System.out.println("Logging in to the server...");
		String response=APIConnector.CallServer_POST(methodAddress, APIConnector.EncodeParameters(parameters), payLoad.get());
	
		Document xML=UtilityFunctions.loadXMLFromString(response);
		String url=UtilityFunctions.GetSubNodeByXPath(xML.getFirstChild(), "/html/body/a/@href").getTextContent();

		SessionToken ret=new SessionToken (
				RWSAPIv3.getTestSessionName(),
				RWSAPIv3.getTestProfileName(),
				GetParamValueFromURL(url, "access_token"),
				Long.parseLong(GetParamValueFromURL(url, "expires_in"))
		);
		return ret;
	}

	Map<String, String> splitQuery(String url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = url.split("#")[1];
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
	
	String GetParamValueFromURL(String url, String paramName) throws UnsupportedEncodingException, MalformedURLException {
		Map<String, String> vals=splitQuery(url);
		return vals.get(paramName);
	}

	private String GetScopeStringForAction(String typeOfScope) {
		String ret=StringUtils.join(GetScopeFor(typeOfScope), " ");
		return ret;
	}
	
	private List<String> GetScopeFor(String typeOfScope) {
		List<String> ret= new ArrayList<String>();
		if(typeOfScope.equals("login") || typeOfScope.equals("sessionLogin")) {
			ret.add("read_account");
			ret.add("read_attendances");
			ret.add("read_backgrounds");
			ret.add("read_break_screens");
			ret.add("read_clockings");
			ret.add("read_photos");
			ret.add("read_device_pings");
			ret.add("read_employees");
			ret.add("read_logs");
			ret.add("read_schedules");
			ret.add("read_server_times");
			ret.add("read_worksites");
			ret.add("write_clockings");
		}
		return ret;
	}

	private String GetAuthMethodURL(String methodName) throws IOException {
		String ret=RWSAPIv3.getTestSessionParameter("BaseAuthUrl");
		ret+="/";
		ret+=methodName;
		return ret;
	}
	
	private String GetAPI2MethodURL(String methodName) throws IOException {
		String ret=RWSAPIv3.getTestSessionParameter("API2_BaseUrl");
		ret+="/";
		ret+=methodName;
		return ret;
	}

}
