package magictester.rws.website.pages;

import java.util.List;

import magictester.core.SuperWebPageAutomator;
import magictester.core.SuperWebsiteAutomator;
import magictester.rws.api.APIV3Request;
import magictester.rws.api.APIV3Response;
import magictester.rws.api.Employee;

public class Page_API extends SuperWebPageAutomator {
	String JSON;
	
	public Page_API(SuperWebsiteAutomator websiteAutomator) {
		super(websiteAutomator);
	}
	
	@Override
	public String GetUrl() {
		return this.WebsiteAutomator.getBaseURL() + "/api/v3";
	}

	public List<Employee> getAllEmployees() throws Exception {
		APIV3Request requ=new APIV3Request();
		APIV3Response resp=this.WebsiteAutomator.CallAPI(this, "employees", requ);
		List<Employee> ret = Employee.ListFactory(resp);
		return ret;
	}

}
