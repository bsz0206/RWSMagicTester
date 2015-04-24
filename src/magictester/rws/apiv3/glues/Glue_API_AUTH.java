package magictester.rws.apiv3.glues;

import magictester.core.MagicTester;
import magictester.core.SuperGlue;
import magictester.core.iTestManager;
import magictester.rws.api.APIAuth;
import magictester.rws.api.TestManager_RWSAPIv3;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class Glue_API_AUTH extends SuperGlue {
	@Override
	protected iTestManager InitializeTestManager() {
		return new TestManager_RWSAPIv3();
	}

	@Override
	protected TestManager_RWSAPIv3 CurrentTestManager() {
		return (TestManager_RWSAPIv3) super.GetCurrentTestManager();
	}

	@Given("^I am using the default API test profile$")
	public void i_am_using_the_default_API_test_profile() throws Throwable {
		CurrentTestManager().LoadTestProfile(MagicTester.DEFAULT_TEST_PROFILE);
	}
	
	@Given("^I am using \"(.*?)\" API test profile$")
	public void i_am_using_test_profile(String testProfileName) throws Throwable {
		CurrentTestManager().LoadTestProfile(testProfileName);
	}

	@When("^I login \\[by APIv3\\]$")
	public void i_login_by_APIv() throws Throwable {
		new APIAuth(CurrentTestManager()).Login();
	}
}
