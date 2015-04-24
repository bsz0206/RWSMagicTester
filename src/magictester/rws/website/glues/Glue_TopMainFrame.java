package magictester.rws.website.glues;

import magictester.core.SuperGlue;
import magictester.core.iTestManager;
import magictester.rws.website.TestManager_RWSWebSite;
import magictester.rws.website.pages.Page_TopMainFrame;
import cucumber.api.java.en.When;

public class Glue_TopMainFrame extends SuperGlue {
	@Override
	protected iTestManager InitializeTestManager() {
		return new TestManager_RWSWebSite();
	}

	@Override
	protected TestManager_RWSWebSite CurrentTestManager() {
		return (TestManager_RWSWebSite) super.GetCurrentTestManager();
	}
	
	@When("^I open the account holder menu$")
	public void i_open_the_account_holder_mneu() throws Throwable {
		(new Page_TopMainFrame(CurrentTestManager())).OpenAccountHolderMenu();
	}

	@When("^I select Settings$")
	public void i_select_Settings() throws Throwable {
		(new Page_TopMainFrame(CurrentTestManager())).SelectSettings();
	}

	@When("^I go to Settings$")
	public void i_go_to_Settings() throws Throwable {
		(new Page_TopMainFrame(CurrentTestManager())).OpenAccountHolderMenu();
		(new Page_TopMainFrame(CurrentTestManager())).SelectSettings();
	}

}
