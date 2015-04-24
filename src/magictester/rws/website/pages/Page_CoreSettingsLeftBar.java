package magictester.rws.website.pages;

import magictester.core.SuperWebPageAutomator;
import magictester.core.SuperWebsiteAutomator;

import org.openqa.selenium.By;

public class Page_CoreSettingsLeftBar extends SuperWebPageAutomator {

	public Page_CoreSettingsLeftBar(SuperWebsiteAutomator websiteAutomator) {
		super(websiteAutomator);
	}
	
	@Override
	public String GetUrl() {
		return this.WebsiteAutomator.getBaseURL();
	}

	public void SelectShiftManagement() {
		ClickOnToolBarItemByTitle("Shift Management");
	}
	
	private void ClickOnToolBarItemByTitle(String itemTitle) {
		By but=By.linkText(itemTitle);
		try {
			SeleniumDriver.findElement(but).click();
		} catch(Exception ex) {
			SeleniumAssistant.ClickByCoordinate(but, 10, 50);
		}	
	}

	public void SelectDayType() {
		ClickOnToolBarItemByTitle("Day Types");
		
	}

}
