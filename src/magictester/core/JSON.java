package magictester.core;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.minidev.json.JSONArray;

import com.jayway.jsonpath.JsonPath;

public class JSON {

	public static String GetStringByJSONPath(String jSON, String jSONPath) {
		return String.valueOf(GetValueByJSONPath(jSON, jSONPath));
	}
	public static Object GetValueByJSONPath(String jSON, String JSONPath) {
		Object ret=null;
		SimpleEntry<String, Object> nameValue=JSON.GetNameValueByJSONPath(jSON, JSONPath);
		ret=nameValue.getValue();
		return ret;
	}
	
	public static SimpleEntry<String, Object> GetNameValueByJSONPath(String JSON, String JSONPath) {
		SimpleEntry<String, Object> ret=null;
		try {
			LinkedHashMap<String, Object> jsonResult = JsonPath.read(JSON, JSONPath);
			Object key = jsonResult.keySet().iterator().next();
		    ret = new SimpleEntry<String, Object>((String)key, jsonResult.get(key));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}
	
	public static List<LinkedHashMap<String, Object>> GetJSONNodesListString(String JSON, String jsonPath) throws Exception {
		List<LinkedHashMap<String, Object>> ret=new ArrayList<LinkedHashMap<String, Object>>();
		JSONArray jsonArray=getJSONArray(JSON, jsonPath);
		if(jsonArray.size()>0 && jsonArray.size()>0) {
			for(int l=0; l<jsonArray.size(); l++) {
				@SuppressWarnings("unchecked")
				LinkedHashMap<String, Object> obj=(LinkedHashMap<String, Object>) jsonArray.get(l);
				ret.add(obj);
			}
		}
		return ret;
	}

	static JSONArray getJSONArray(String jsonstring, String jsonPath) throws Exception {
		JSONArray ret=null;
		try {
			ret = JsonPath.read(jsonstring, jsonPath);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (ret==null) {
			throw new Exception("Invalid JSON path provided!");
		} else if (ret instanceof List && ((List<?>) ret).isEmpty()) {
			return null;
		}
		
		return ret;
	}


}
