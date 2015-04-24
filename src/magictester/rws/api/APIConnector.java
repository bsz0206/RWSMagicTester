package magictester.rws.api;

import java.io.UnsupportedEncodingException;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

public class APIConnector {
	
	public static String CallServer_POST(String methodAddress, String encodeParameters, String payload) throws UnsupportedEncodingException {
		String fullURL=methodAddress+encodeParameters;
		Response  webResponse = getWebRequest(fullURL).post(Entity.entity(payload, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);
		
		String ret =  webResponse.readEntity(String.class);
		return ret;
	}

	static String EncodeParameters(List<SimpleEntry<String, String>> parameters) {
		String parametersString="";
		if(parameters!=null && parameters.size()>0) {
			parametersString+="?";
			for(int lop=0; lop<parameters.size(); lop++){
				if(lop>0) parametersString+="&";
				SimpleEntry<String, String> item = parameters.get(lop);
				parametersString+=item.getKey();
				parametersString+="=";
				parametersString+=item.getValue();
			}
		}
		
		return parametersString;
	}

	public static String CallServer_GET(String methodAddress) throws UnsupportedEncodingException {
		return APIConnector.CallServer_GET(methodAddress, null);
	}
	
	public static String CallServer_GET(
			String methodAddress, List<SimpleEntry<String, String>> parameters) 
					throws UnsupportedEncodingException {
		String parametersString=APIConnector.EncodeParameters(parameters);
		String fullURL=methodAddress+parametersString;
		Builder webRequest=getWebRequest(fullURL);
		Response  webResponse = webRequest.get();
		if (webResponse.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + webResponse.getStatus());
		}

		String ret=webResponse.readEntity(String.class);
		return ret;
	}

	public static Builder getWebRequest(String fullURL) {
		//ClientConfig config = new ClientConfig();
		//Client webClient = ClientBuilder.newClient();
		//ClientConfig clientConfig = new ClientConfig();
		//((Object) clientConfig).get .getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		
		//config.property(ClientProperties.PROXY_URI, "127.0.0.1:8888");
		//webClient.property(ClientProperties.PROXY_URI, "http://127.0.0.1:8888");
		//System.setProperty("http.proxyHost","127.0.0.1");
		//System.setProperty("http.proxyPort", "8888");
		//System.setProperty("https.proxyHost","127.0.0.1");
		//System.setProperty("https.proxyPort", "8888");
        //cc.property(ClientProperties.PROXY_USERNAME, "user");
        //cc.property(ClientProperties.PROXY_PASSWORD, "pass");

		//((Object) clientConfig).get .getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		//clientBuilder.sslContext(sslContext).hostnameVerifier(allHostsValid).withConfig(config).build();
		
		Client webClient = ClientBuilder.newClient();

		WebTarget webTarget;
		webTarget=webClient.target(fullURL);
		webTarget.property(ClientProperties.FOLLOW_REDIRECTS, false);
		Builder webRequest=webTarget.request();
		webRequest.header("User-Agent", "Mozilla/5.0 (Linux; Android 4.4.2; SGH-T999V Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");

		//webRequest.header("Content-Length", Integer.toString(payload.length()));
		//webRequest.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		//webRequest.header("Content-Type", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		//webRequest.header("Origin", "https://auth.RWS.com");
		//webRequest.header("Referer", lastURL);
		//webRequest.header("Referer", 
				//UtilityFunctions.URLEncode(
						//"https://auth.RWS.com/oauth/authorize?client_id=5v2pkyl7krni12qtcxfazgbz8ptu7nj&redirect_uri=https://auth.RWS.com/_status&response_type=token&scope=read_account%20read_attendances%20read_backgrounds%20read_break_screens%20read_clockings%20read_photos%20read_device_pings%20read_employees%20read_logs%20read_schedules%20read_server_times%20read_worksites%20write_clockings&state=e30&webflow=true"
						//)
						//);
		//webRequest.header("Accept-Encoding", "gzip,deflate");
		//webRequest.header("Accept-Language", "en-CA,en-US;q=0.8");
		//webRequest.header("X-Requested-With", "com.teamclock.android");
		return webRequest;
	}
}
