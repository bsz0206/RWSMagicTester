package magictester.rws.website.pages;

import java.util.ArrayList;
import java.util.List;

import magictester.core.ArrayComparer;
import magictester.core.PageField;
import magictester.core.SuperWebPageAutomator;
import magictester.core.SuperWebsiteAutomator;
import magictester.core.UtilityFunctions;
import magictester.rws.website.RWSCommonActionResult;
import magictester.rws.website.TestManager_RWSWebSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Page_EmployeeManualSettings extends SuperWebPageAutomator {

	public Page_EmployeeManualSettings(SuperWebsiteAutomator websiteAutomator) {
		super(websiteAutomator);
		AddFieldDefinitions();
	}
	
	@Override
	public String GetUrl() {
		return this.WebsiteAutomator.getBaseURL()+"/employees/new";
	}

	public void OpenPage() {
		SeleniumAssistant.OpenPage(this.GetUrl());
	}

	void AddFieldDefinitions() {
		PageFieldList.add(new PageField("UIDialog", By.xpath(UtilityFunctions.getXPathForOneOfClassIS("ui-dialog")))); 

		PageFieldList.add(new PageField("Username", "user_username", ControlType.textbox));
		PageFieldList.add(new PageField("Password", "user_password", ControlType.textbox));
		PageFieldList.add(new PageField("Confirm Password",  "user_password_confirmation", ControlType.textbox));
		PageFieldList.add(new PageField("First Name", "user_firstname", ControlType.textbox));
		PageFieldList.add(new PageField("Surname",    "user_lastname", ControlType.textbox));
		PageFieldList.add(new PageField("System Permissions",  "employee_role_id", ControlType.dropdown));
		PageFieldList.add(new PageField("Gender",  "user_gender", ControlType.dropdown));
		PageFieldList.add(new PageField("Status",  "employee_status", ControlType.textbox));
		PageFieldList.add(new PageField("Salutation",  "user_salutation", ControlType.textbox));
		PageFieldList.add(new PageField("Additional Name(s)",  "user_middlename", ControlType.textbox));
		PageFieldList.add(new PageField("Mother's Maiden Name",  "user_mothers_maiden_name", ControlType.textbox));
		PageFieldList.add(new PageField("Name extension",  "user_name_extension", ControlType.textbox));
		PageFieldList.add(new PageField("Nickname",  "user_nickname", ControlType.textbox));
		PageFieldList.add(new PageField("Email",  "employee_email", ControlType.textbox));
		PageFieldList.add(new PageField("Personal Email",  "user_email", ControlType.textbox));
		PageFieldList.add(new PageField("Employee Number",  "employee_employee_number", ControlType.textbox));
		PageFieldList.add(new PageField("Hours per day override",  "employee_hours_per_day_override", ControlType.textbox));
		PageFieldList.add(new PageField("Tax rule",  "employee_tax_rule_id", ControlType.textbox));
		PageFieldList.add(new PageField("Pay mode",  "employee_pay_mode", ControlType.textbox));
		PageFieldList.add(new PageField("Employment Date",  "employee_employment_date", ControlType.textbox));
		PageFieldList.add(new PageField("Termination Date",  "employee_termination_date", ControlType.textbox));
		PageFieldList.add(new PageField("Termination Code",  "employee_termination_code", ControlType.textbox));
		PageFieldList.add(new PageField("Allow non-scheduled clock ins?",  "employee_allowed_to_clock_on_non_work_days", ControlType.textbox));
		PageFieldList.add(new PageField("Works on Holidays?",  "employee_works_on_holidays", ControlType.textbox));
		PageFieldList.add(new PageField("Allowed to Clock Offsite?",  "employee_allowed_to_clock_offsite", ControlType.textbox));
		PageFieldList.add(new PageField("Primary Worksite",  "employee_worksite_id", ControlType.textbox));
		PageFieldList.add(new PageField("Address1",  "user_address1", ControlType.textbox));
		PageFieldList.add(new PageField("Address2",  "user_address2", ControlType.textbox));
		PageFieldList.add(new PageField("Address3",  "user_address3", ControlType.textbox));
		PageFieldList.add(new PageField("City",  "user_city", ControlType.textbox));
		PageFieldList.add(new PageField("Postal code",  "user_postal_code", ControlType.textbox));
		PageFieldList.add(new PageField("Province",  "user_province", ControlType.textbox));
		PageFieldList.add(new PageField("Country",  "user_country", ControlType.textbox));
		PageFieldList.add(new PageField("Emergency Contact Person",  "user_emergency_name", ControlType.textbox));
		PageFieldList.add(new PageField("Emergency Contact Address",  "user_emergency_address", ControlType.textbox));
		PageFieldList.add(new PageField("Emergency Contact Phone",  "user_emergency_phone", ControlType.textbox));
		PageFieldList.add(new PageField("Date of Birth",  "user_birthdate", ControlType.textbox));
		PageFieldList.add(new PageField("Marital Status",  "user_marital_status", ControlType.textbox));
		PageFieldList.add(new PageField("Number of Children",  "user_number_of_children", ControlType.textbox));
		
		//PageFieldList.add(new PageField("XXXXX",  "employee_employee_phones_attributes_1_phone_number", ControlType.textbox));
		//PageFieldList.add(new PageField("XXXXX",  "employee_employee_phones_attributes_1_label", ControlType.textbox));
		//PageFieldList.add(new PageField("XXXXX",  "$('#new_employee_phone_0').remove();", ControlType.textbox));
		//PageFieldList.add(new PageField("Currently allowed to clock in/out from any IP.",  "YYYY", ControlType.textbox));
	}

	public List<String> GetErrorFieldLabels() {
		List<String> ret=new ArrayList<String>();
		for (PageField pf : this.PageFieldList) {
			String classOf=SeleniumAssistant.GetAttributeByID(pf.ID, "class");
			if(classOf.contains("error")) {
				ret.add(pf.Label);
			}
		}
		return ret;
	}
	
	public List<String> GetOKFieldLabels() {
		return ArrayComparer.GetSinglesAtLeft(this.GetAllFieldLabels(), GetErrorFieldLabels());
	}

	Boolean submitCompeleted=false;
	public void Submit() {
		submitCompeleted=false;
		SeleniumAssistant.TextBox_SetText("user_number_of_children", SeleniumAssistant.Textbox_GetText("user_number_of_children")); //Selenium ChromeDriver bug workaround
		SeleniumAssistant.Button_ByName_Click("commit");
		//new Page_TopMainFrame(WebsiteAutomator).getActionResults();
	}
	
	public Boolean EmployeeCreated() throws Exception {
		//#if(!submitCompeleted) return false;
		
		RWSCommonActionResult LastActionResult=((TestManager_RWSWebSite)this.WebsiteAutomator)
				.getActionResultsAndCloseDialogBoxes();
		
		LastActionResult.Print();

		if(LastActionResult.ErrorReported()) {
			return false;
		}

		By by=GetFieldByCode("UIDialog").By;
		List<ExpectedCondition<WebElement>> itemExpectations = new ArrayList<ExpectedCondition<WebElement>>();
		itemExpectations.add(ExpectedConditions.presenceOfElementLocated(by));
		if(SeleniumAssistant.WaitForConditions(itemExpectations, 1, 1, 1000)) {		
			List<WebElement> dialogs=SeleniumDriver.findElements(by);
			if(!dialogs.isEmpty()) {
				for(WebElement we : dialogs) {
					WebElement buttonPane=we.findElement(By.xpath(UtilityFunctions.getXPathForOneOfClassIS("ui-dialog-buttonpane")));
					buttonPane.findElement(By.tagName("button")).click();
				}
			}
		}
		
		return true;
	}

}
