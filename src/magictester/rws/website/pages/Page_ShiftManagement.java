package magictester.rws.website.pages;

import magictester.core.PageField;
import magictester.core.SuperWebPageAutomator;
import magictester.core.SuperWebsiteAutomator;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class Page_ShiftManagement extends SuperWebPageAutomator {

	public Page_ShiftManagement(SuperWebsiteAutomator websiteAutomator) {
		super(websiteAutomator);
		PageFieldList.add(new PageField("ShiftStart", By.id("shift_shift_items_attributes_0_start_time_string"))); 
		PageFieldList.add(new PageField("ShiftEnd",   By.id("shift_shift_items_attributes_0_end_time_string"))); 
		PageFieldList.add(new PageField("CreateShiftButton", By.name("commit"))); 
		PageFieldList.add(new PageField("NewCategoryNameTextbox", By.id("select_with_more_new_value"))); 
		PageFieldList.add(new PageField(
				"btCreateCategoryOK", 
				By.xpath("//span[text()= \"Create a Category Name\"]/../..//button[@type=\"button\" and text()=\"OK\"]"))
		); 
	}
	
	@Override
	public String GetUrl() {
		return this.WebsiteAutomator.getBaseURL() + "/setup/shifts";
	}

	public void clickAddNewShift() {
	    SeleniumDriver.findElement(By.cssSelector("span.icon.add_link")).click();
	}

	public void enterShiftStart(String shiftStart) {
		//SeleniumDriver.findElement(By.id("shift_shift_items_attributes_0_start_time_string")).clear();
		//SeleniumDriver.findElement(By.id("shift_shift_items_attributes_0_start_time_string")).sendKeys(shiftStart);
		SeleniumAssistant.SetText(GetFieldByCode("ShiftStart").By, shiftStart);
	}

	public void enterShiftEnd(String shiftEnd) {
		//SeleniumDriver.findElement(By.id("shift_shift_items_attributes_0_end_time_string")).clear();
		//SeleniumDriver.findElement(By.id("shift_shift_items_attributes_0_end_time_string")).sendKeys(shiftEnd);
		SeleniumAssistant.SetText(GetFieldByCode("ShiftEnd").By, shiftEnd);
	}

	public void clickCreateShift() {
		SeleniumAssistant.ClickFailSafe(GetFieldByCode("CreateShiftButton").By);
	}

	public void selectCategory(String category) {
	    new Select(SeleniumDriver.findElement(By.id("shift_category_name"))).selectByVisibleText(category);
	}

	public void createCategory(String category) {
		//See the line below //new Select(SeleniumDriver.findElement(By.id("shift_category_name"))).selectByVisibleText("Create New Category");
		selectCategory(category);
		// See the line below //SeleniumDriver.findElement(By.id("select_with_more_new_value")).clear();
		// See the line below //SeleniumDriver.findElement(By.id("select_with_more_new_value")).sendKeys(category);
		enterNewCategoryName(category);
		// See the line below //SeleniumDriver.findElement(By.xpath("//span[text()= \"Create a Category Name\"]/../..//button[@type=\"button\" and text()=\"OK\"]")).click();
		pressCreateCategoryOKButton();
	 }

	public void enterNewCategoryName(String category) {
		////SeleniumDriver.findElement(By.id("select_with_more_new_value")).clear();
		////SeleniumDriver.findElement(By.id("select_with_more_new_value")).sendKeys(category);
		//SeleniumDriver.findElement(GetFieldByCode("NewCategoryNameTextbox").By).clear();
		//SeleniumDriver.findElement(GetFieldByCode("NewCategoryNameTextbox").By).sendKeys(category);
		SeleniumAssistant.SetText(GetFieldByCode("NewCategoryNameTextbox").By, category);
	}

	public void pressCreateCategoryOKButton() {
		//SeleniumDriver.findElement(By.xpath("//span[text()= \"Create a Category Name\"]/../..//button[@type=\"button\" and text()=\"OK\"]")).click();
		SeleniumDriver.findElement(GetFieldByCode("btCreateCategoryOK").By).click();
	}
}
