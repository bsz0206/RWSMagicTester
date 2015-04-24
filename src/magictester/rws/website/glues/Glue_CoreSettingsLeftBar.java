package magictester.rws.website.glues;

import magictester.core.SuperGlue;
import magictester.core.iTestManager;
import magictester.rws.website.TestManager_RWSWebSite;
import magictester.rws.website.pages.Page_CoreSettingsLeftBar;
import cucumber.api.java.en.When;

public class Glue_CoreSettingsLeftBar extends SuperGlue {
	@Override
	protected iTestManager InitializeTestManager() {
		return new TestManager_RWSWebSite();
	}

	@Override
	protected TestManager_RWSWebSite CurrentTestManager() {
		return (TestManager_RWSWebSite) super.GetCurrentTestManager();
	}

	@When("^I select Shift Management from left side bar$")
	public void i_select_Shift_Management_from_left_side_bar() throws Throwable {
		new Page_CoreSettingsLeftBar(CurrentTestManager()).SelectShiftManagement();
	}
	
	@When("^I select Day Type from left side bar$")
	public void i_select_Day_Type_from_left_side_bar() throws Throwable {
		new Page_CoreSettingsLeftBar(CurrentTestManager()).SelectDayType();
	}
}
