package magictester.rws.apiv3.glues;

import java.util.List;

import magictester.core.SuperGlue;
import magictester.rws.api.API_v3;
import magictester.rws.api.Employee;
import magictester.rws.api.TestManager_RWSAPIv3;
import cucumber.api.java.en.When;

public class Glue_APIv3 extends SuperGlue {
	@Override
	protected TestManager_RWSAPIv3 InitializeTestManager() {
		return new TestManager_RWSAPIv3();
	}

	@Override
	protected TestManager_RWSAPIv3 CurrentTestManager() {
		return (TestManager_RWSAPIv3) super.GetCurrentTestManager();
	}

	@When("^I get all employees \\[by APIv3\\]$")
	public void by_APIv_I_get_all_employees() throws Throwable {
		List<Employee> ret=(new API_v3(CurrentTestManager())).getAllEmployees();
		for(Employee r : ret) {
			System.out.println(r.FirstName + " " + r.LastName);
		}
	}
}
