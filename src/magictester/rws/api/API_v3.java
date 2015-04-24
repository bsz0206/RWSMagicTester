package magictester.rws.api;

import java.io.IOException;
import java.util.List;

public class API_v3 {
	TestManager_RWSAPIv3 testManager_RWSAPIv3;
	SessionToken sessionToken;
	
	public API_v3(TestManager_RWSAPIv3 testManager_RWSAPIv3) {
		this.testManager_RWSAPIv3=testManager_RWSAPIv3;
	}

	private APIV3Response callMethod(String methodName) throws Exception {
		APIV3Response ret  = new APIV3Response();
		sessionToken = new APIAuth(testManager_RWSAPIv3).Login();
		String url=GetAPI3MethodURL(methodName);
		url+="?token=" + sessionToken.getToken();
		String jSONContent=APIConnector.CallServer_GET(url);
		ret.LoadJSON(jSONContent);
		return ret;
	}

	public List<Employee> getAllEmployees() throws Exception {
		return Employee.ListFactory(callMethod("employees"));
	}

	private String GetAPI3MethodURL(String methodName) throws IOException {
		String ret=testManager_RWSAPIv3.getTestSessionParameter("API3_BaseUrl");
		ret+="/";
		ret+=methodName;
		return ret;
	}

}
