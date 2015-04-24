package magictester.rws.website;

import magictester.core.ChiefManager;
import magictester.core.MagicTester;
import magictester.core.SuperWebsiteAutomator;
import magictester.rws.website.pages.Page_Login;
import magictester.rws.website.pages.Page_TopMainFrame;
import cucumber.api.Scenario;

public class TestManager_RWSWebSite extends SuperWebsiteAutomator {
	public String account;
	public String company;
	public String username;
	public String password;

	@Override
	public String getTestProfileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTestProfileName(String userProfileName) {
		// TODO Auto-generated method stub
	}

	@Override
	public String[] getGluePackages() {
		String[] ret= new String[1];
		ret[0]="magictester.ph.website.glues";
		return ret;
	}

	@Override
	public String getTestAreaName() {
		return "RWS Website";
	}

	@Override
	public String getBaseURL() {
		return "http://" + account + "." + ChiefManager.getInstance().getRunParameter("RWS Website", "BaseURL");
	}

	public TestManager_RWSWebSite() {
		this(MagicTester.DEFAULT_TEST_PROFILE);
	}

	public TestManager_RWSWebSite(String testProfile) {
		LoadTestProfile(testProfile);
	}
	
	public void LoadTestProfile(String testProfile) {
		this.account =getTestProfileParameter(testProfile, "Account");
		this.username=getTestProfileParameter(testProfile, "Username");
		this.password=getTestProfileParameter(testProfile, "Password");
		this.company =getTestProfileParameter(testProfile, "Company");
	}

	public Boolean Login() throws Exception {
		Page_Login page_Login= new Page_Login(this);
		return page_Login.LoginAs(this.username, this.password);
	}

	@Override
	public void RunScenarioTagBefore(Scenario scenario, String tag) {
		//System.out.println("before_" + tag + "=" + scenario.getName());
	}

	@Override
	public void RunScenarioTagAfter(Scenario scenario, String tag) {
		//System.out.println("after_" + tag + "=" + scenario.getName());
		if(tag.equals("FeatureCloseBrowserOnSuccess")) {
			CloseAllBrowserInstancs();
		}
	}

	public RWSCommonActionResult getActionResultsAndCloseDialogBoxes() throws Exception {
		return (new Page_TopMainFrame(this)).getActionResultsAndCloseDialogBoxes();
	}

}
