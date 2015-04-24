package magictester.rws.website.pages;

import magictester.core.SuperWebPageAutomator;
import magictester.core.SuperWebsiteAutomator;
import magictester.core.UtilityFunctions;
import magictester.rws.website.TestManager_RWSWebSite;

import org.openqa.selenium.By;

public class Page_Login extends SuperWebPageAutomator {

	public Page_Login(SuperWebsiteAutomator websiteAutomator) {
		super(websiteAutomator);
	}
	
	@Override
	public String GetUrl() {
		return this.WebsiteAutomator.getBaseURL();
	}

	public void Enter_UserName(String userName) throws Exception {
		this.SeleniumAssistant.SetText(By.id("username"), userName);
	}

	public void Enter_Password(String password) throws Exception {
		this.SeleniumAssistant.SetText(By.id("password"), password);
	}

	public void Submit() {
		Button_ByName_Click("commit");
	}

	public Boolean LoginAs(String loginName, String password) throws Exception {
		OpenPageByURL();
		Enter_UserName(loginName);
		Enter_Password(password);
		Submit();
		return CheckUserIsLoggedIn(loginName);
	}

	public Boolean CheckUserIsLoggedIn(String loginName) {
		for(int lop=0; lop<5; lop++) {
			if(CheckUserIsLoggedInDo(loginName))
				return true;
			UtilityFunctions.Sleep(100);
		}
		return false;
	}
	
	private Boolean CheckUserIsLoggedInDo(String loginName) {
		TestManager_RWSWebSite phCompanySite=((TestManager_RWSWebSite) this.WebsiteAutomator);
		return this.GetTitle().contains(phCompanySite.company);
	}
}
