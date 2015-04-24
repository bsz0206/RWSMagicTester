package magictester.rws.api;

import java.util.LinkedHashMap;
import java.util.List;

import magictester.core.JSON;
import magictester.core.UtilityFunctions;
import net.minidev.json.JSONArray;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jayway.jsonpath.JsonPath;

public class APIV3Response {
	String jSONContent;
	
	public APIV3Response() {}
	
	public void LoadJSON(String jSONContent) {
		this.jSONContent=jSONContent;
	}
	
	public void LoadHTML(String respHTML) {
		Document respXML=UtilityFunctions.loadXMLFromString(respHTML);
		Element el=(Element) UtilityFunctions.GetSubNodeByXPath(respXML, "/html/body");
		jSONContent=el.getTextContent();
	}
	
	public String getJSON() {
		return jSONContent;
	}

	public List<LinkedHashMap<String, Object>> GetJSONNodesListString(String jsonPath) throws Exception {
		return JSON.GetJSONNodesListString(jSONContent, jsonPath);
	}

	JSONArray getJSONArray(String jsonstring, String jsonPath) throws Exception {
		JSONArray ret = JsonPath.read(jsonstring, jsonPath);

		if (null == ret) {
			throw new Exception("Invalid JSON path provided!");
		} else if (ret instanceof List && ((List<?>) ret).isEmpty()) {
			return null;
		}
		
		return ret;
	}
}
