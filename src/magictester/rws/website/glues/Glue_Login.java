package magictester.rws.website.glues;

import static org.junit.Assert.assertTrue;
import magictester.core.MagicTester;
import magictester.core.SuperGlue;
import magictester.rws.website.TestManager_RWSWebSite;
import cucumber.api.java.en.Given;

public class Glue_Login extends SuperGlue {
	@Override
	protected TestManager_RWSWebSite InitializeTestManager() {
		return new TestManager_RWSWebSite();
	}

	@Override
	protected TestManager_RWSWebSite CurrentTestManager() {
		return (TestManager_RWSWebSite) super.GetCurrentTestManager();
	}

	@Given("^I am using the default test profile$")
	public void i_am_using_defauult_profile() throws Throwable {
		CurrentTestManager().LoadTestProfile(MagicTester.DEFAULT_TEST_PROFILE);
	}
	
	@Given("^I am using the test profile \"(.*?)\"$")
	public void i_am_using_test_profile(String testProfileName) throws Throwable {
		CurrentTestManager().LoadTestProfile(testProfileName);
	}

	@Given("^I login$")
	public void i_login() throws Throwable {
		assertTrue("Login failed.", CurrentTestManager().Login());
	}
}
