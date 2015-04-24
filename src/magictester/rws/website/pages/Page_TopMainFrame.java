package magictester.rws.website.pages;

import magictester.core.SuperWebPageAutomator;
import magictester.core.SuperWebsiteAutomator;
import magictester.rws.website.RWSCommonActionResult;

import org.openqa.selenium.By;

public class Page_TopMainFrame extends SuperWebPageAutomator {
	public   Page_TopMainFrame(SuperWebsiteAutomator websiteAutomator) {
		super(websiteAutomator);
		if(websiteAutomator==null) return; // for simulation tests only
		PageField_Add("AccountOwnerTriangleMenu", By.xpath("//*[@id=\"header\"]//*[@class=\"drop-down\"]"));
	}
	
	@Override
	public String GetUrl() {
		return this.WebsiteAutomator.getBaseURL();
	}

	public void OpenAccountHolderMenu() throws Exception {
		//CheckMainPageIsFree();
		//By accountOwnerMenu=By.linkText("Elham Entekhabi-Fard");
		//By accountOwnerMenu=By.xpath("//*[@id=\"header\"]//*[@class=\"drop-down\"]");
		//SeleniumDriver.findElement(accountOwnerMenu).click();
		
		By by=GetFieldByCode("AccountOwnerTriangleMenu").By;
		SeleniumAssistant.WaitForElementToClick(by);
	}

	public void SelectSettings() {
		//CheckMainPageIsFree();
		SeleniumDriver.findElement(By.linkText("Settings")).click();
	}
	
	public RWSCommonActionResult getActionResultsAndCloseDialogBoxes() throws Exception {
		RWSCommonActionResult ret=new RWSCommonActionResult(SeleniumDriver, SeleniumAssistant);
		ret.getResults();
		ret.WaitForAppearanceThenCloseAllResultDialogBoxes();
		return ret;
	}
	
	/*public Boolean CheckMainPageIsFree() {
		CloseAllErrorDialogs();
		Boolean ret=SeleniumAssistant.WaitUntilEitherHappens (
				ExpectedConditions.elementToBeClickable(GetFieldByCode("AccountOwnerTriangleMenu").By),
				ExpectedConditions.presenceOfElementLocated(GetFieldByCode("AccountOwnerTriangleMenu").By)
		);
		return ret;
	}*/
	
}
