package magictester.core.glues;

import magictester.core.ChiefManager;
import magictester.core.MagicTester;
import magictester.core.SuperGlue;
import magictester.core.iTestManager;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class Glue_MagicManager extends SuperGlue {
	@Override
	protected iTestManager InitializeTestManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected iTestManager CurrentTestManager()  {
		return null; //Not needed here
	}

	@Given("^I am testing application \"(.*?)\"$")
	public void i_am_testing(String testManagerAreaName) throws Throwable {
		ChiefManager.getInstance().SetCurrentTestManagerByAreaName(testManagerAreaName, MagicTester.DEFAULT_TEST_SESSION);
	}

	@Given("^In the default test session I am testing application \"(.*?)\"$")
	public void i_am_testing_in_default_session(String testManagerAreaName) throws Throwable {
		ChiefManager.getInstance().SetCurrentTestManagerByAreaName(testManagerAreaName, MagicTester.DEFAULT_TEST_SESSION);
	}

	@Given("^In the test session \"(.*?)\" I am testing application \"(.*?)\"$")
	public void in_the_test_session_I_am_testing_application(String userSession, String testManagerAreaName) throws Throwable {
		ChiefManager.getInstance().SetCurrentTestManagerByAreaName(testManagerAreaName, userSession);
	}

	@When("^I use the default test session$")
	public void i_switch_to_default_session1() throws Throwable {
		ChiefManager.getInstance().SwitchCurrentUserSession(MagicTester.DEFAULT_TEST_PROFILE);
	}

	@When("^I switch to the default test session$")
	public void i_switch_to_default_session2() throws Throwable {
		ChiefManager.getInstance().SwitchCurrentUserSession(MagicTester.DEFAULT_TEST_PROFILE);
	}

	@When("^I switch to \"(.*?)\" test session$")
	public void i_switch_to_user_session(String userSession) throws Throwable {
		ChiefManager.getInstance().SwitchCurrentUserSession(userSession);
	}
}
