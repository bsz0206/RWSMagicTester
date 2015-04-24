package magictester.rws.website.glues;

import java.util.List;

import magictester.core.SuperGlue;
import magictester.core.iTestManager;
import magictester.rws.api.Employee;
import magictester.rws.website.TestManager_RWSWebSite;
import magictester.rws.website.pages.Page_API;
import cucumber.api.java.en.When;

public class Glue_API extends SuperGlue {
	@Override
	protected iTestManager InitializeTestManager() {
		return new TestManager_RWSWebSite();
	}

	@Override
	protected TestManager_RWSWebSite CurrentTestManager() {
		return (TestManager_RWSWebSite) super.GetCurrentTestManager();
	}

	@When("^I get all employees \\[by WebAPI\\]$")
	public void by_APIv_I_get_all_employees() throws Throwable {
		List<Employee> ret=(new Page_API(CurrentTestManager())).getAllEmployees();
		for(Employee r : ret) {
			System.out.println(r.FirstName + " " + r.LastName);
		}
	}
}
