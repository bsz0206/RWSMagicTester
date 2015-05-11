package magictester.rws.website.glues;

import java.util.List;
import java.util.Map;

import magictester.core.SuperGlue;
import magictester.core.iTestManager;
import magictester.rws.website.TestManager_RWSWebSite;
import magictester.rws.website.pages.Page_CoreSettingsLeftBar;
import magictester.rws.website.pages.Page_ShiftManagement;
import magictester.rws.website.pages.Page_ShiftManagement.Shift;
import magictester.rws.website.pages.Page_TopMainFrame;

import org.junit.Assert;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Glue_ShiftManagement  extends SuperGlue {
	

	@Override
	protected iTestManager InitializeTestManager() {
		return new TestManager_RWSWebSite();
	}

	@Override
	protected TestManager_RWSWebSite CurrentTestManager() {
		return (TestManager_RWSWebSite) super.GetCurrentTestManager();
	}

	@When("^I go to Settings > Shift Management$")
	public void i_go_to_ShiftManagement() throws Throwable {
		go_to_shift_management();
	}

	@When("^I go to enter the Shift Management page URL$")
	public void I_go_to_enter_the_Shift_Management_page_URL() throws Throwable {
		(new Page_ShiftManagement(CurrentTestManager())).OpenPageByURL();
	}
	@When("^I click on Add a new Shift button$")
	public void i_click_on_Add_a_new_Shift_button() throws Throwable {
		(new Page_ShiftManagement(CurrentTestManager())).clickAddNewShift();
	}
	
	@When("^I create a Category named \"(.*?)\"$")
	public void I_create_a_Category_named(String category) throws Throwable {
		(new Page_ShiftManagement(CurrentTestManager())).createCategory(category);
	}

	@When("^I enter \"(.*?)\" as new Cateogry name$")
	public void i_enter_as_new_Cateogry_name(String category) throws Throwable {
		(new Page_ShiftManagement(CurrentTestManager())).enterNewCategoryName(category);
	}
	
	@When("^I press OK to create a new Cateogry$")
	public void i_press_OK_to_create_a_new_Cateogry() throws Throwable {
		(new Page_ShiftManagement(CurrentTestManager())).pressCreateCategoryOKButton();
	}

	@When("^I select \"(.*?)\" from the Category dropdown$")
	public void i_select_fron_Category_dropdown(String category) throws Throwable {
		(new Page_ShiftManagement(CurrentTestManager())).selectCategory(category);
	}

	@When("^I select to Create New Category from the Category dropdown$")
	public void i_select_to_Create_New_Category_from_the_Category_dropdown() throws Throwable {
	    i_select_fron_Category_dropdown("Create New Category");
	}

	
	@When("^I enter \"(.*?)\" into Shift Start textbox$")
	public void i_enter_into_Shift_Start_textbox(String shiftStart) throws Throwable {
		(new Page_ShiftManagement(CurrentTestManager())).enterShiftStart(shiftStart);
	}
	
	@When("^I enter \"(.*?)\" into Shift End textbox$")
	public void i_enter_into_Shift_End_textbox(String shiftEnd) throws Throwable {
		(new Page_ShiftManagement(CurrentTestManager())).enterShiftEnd(shiftEnd);
	}

	@When("^I click on Create Shift button$")
	public void i_click_on_Create_Shift_button() throws Throwable {
		(new Page_ShiftManagement(CurrentTestManager())).clickCreateShift();
	}
	
	@When("^I create a shift from \"(.*?)\" to \"(.*?)\"$")
	public void i_create_a_shift_from_to(String shiftStart, String shiftEnd) throws Throwable {
		go_to_shift_management();
		(new Page_ShiftManagement(CurrentTestManager())).clickAddNewShift();
		(new Page_ShiftManagement(CurrentTestManager())).enterShiftStart(shiftStart);
		(new Page_ShiftManagement(CurrentTestManager())).enterShiftEnd(shiftEnd);
		(new Page_ShiftManagement(CurrentTestManager())).clickCreateShift();
	}   
	@Then("^I have a category named \"(.*?)\"$")
	public void i_have_a_category_name(String cat) throws Throwable {
		List<String> existingCats = (new Page_ShiftManagement(CurrentTestManager())).getAllCategories();
		Assert.assertTrue(existingCats.contains(cat));
		
	}

	@Then("^in category \"(.*?)\" I have a shift \"(.*?)\" to \"(.*?)\" with \"(.*?)\" hours total$")
	public void in_category_I_have_a_shift_from_to_with_hours_total(String cat, String shiftStart, String shiftEnd, String duration) throws Throwable {
		(new Page_ShiftManagement(CurrentTestManager())).clickOnCategory(cat);
		List<Shift> catItems = (new Page_ShiftManagement(CurrentTestManager())).getCatItems(cat);
		boolean found = false;
		for(Shift item : catItems){
			if (
					shiftStart.equals(item.start) &&
					shiftEnd.equals(item.end) &&
					duration.equals(item.duration)
					){
				found=true;
				break;
			}
		}
		Assert.assertTrue("Shift Not Found", found);
	}
	
	// Throwable or Exception, because OpenAccountHolderMenu may cause an exception (error)
	void go_to_shift_management () throws Exception{
		(new Page_TopMainFrame(CurrentTestManager())).OpenAccountHolderMenu();
		(new Page_TopMainFrame(CurrentTestManager())).SelectSettings();
		(new Page_CoreSettingsLeftBar(CurrentTestManager())).SelectShiftManagement();
	}
	
	@When("^I create a shift which starts at \"(.*?)\" and ends at \"(.*?)\" in category \"(.*?)\"$")
		public void i_create_a_shift_which_starts_at_and_ends_at_in_category(String shiftStart, String shiftEnd, String category) throws Throwable {
			go_to_shift_management();
			Page_ShiftManagement page_ShiftManagement=new Page_ShiftManagement(CurrentTestManager());
			
			page_ShiftManagement.clickAddNewShift();
			i_select_fron_Category_dropdown("Create New Category");
			page_ShiftManagement.enterNewCategoryName(category);
			page_ShiftManagement.pressCreateCategoryOKButton();
			page_ShiftManagement.selectCategory(category);
			page_ShiftManagement.enterShiftStart(shiftStart);
			page_ShiftManagement.enterShiftEnd(shiftEnd);
			page_ShiftManagement.clickCreateShift();
	}

	
	
	@Then("^in category \"(.*?)\" I have a shift from \"(.*?)\" to \"(.*?)\" with \"(.*?)\" minutes total$")
	public void in_category_I_have_a_shift_from_to_with_minutes_total(String cat, String shiftStart, String shiftEnd, String durationMin) throws Throwable {
		
		go_to_shift_management();
		// Converting string to Double (decimal number)
		double duratioHour = Double.parseDouble(durationMin)/60;
				
		// Converting double to string
		String durationHourString = String.valueOf(duratioHour);
		
		// I called the existing function
		i_have_a_category_name(cat);
		
		// I called the existing function
		in_category_I_have_a_shift_from_to_with_hours_total(cat, shiftStart, shiftEnd, durationHourString);
	
	}
	
	@When("^I create the following shifts:$")
	public void i_create_the_following_shifts(DataTable dataTable) throws Throwable {
		for (Map<String, String> map : dataTable.asMaps(String.class, String.class)) {
	        String cateogryName = map.get("Cateogry Name");
	        String start  = map.get("Start");
	        String end  = map.get("End");
	        //as the variable has been defined in for so out of that doen't have meaning
	        i_create_a_shift_which_starts_at_and_ends_at_in_category(start, end, cateogryName);
		}
		
	}

	@Then("^the following shifts exists:$")
	public void the_following_shifts_exists(DataTable dataTable) throws Throwable {
		for (Map<String, String> map : dataTable.asMaps(String.class, String.class)) {
	        String cateogryName = map.get("Cateogry Name");
	        String start  = map.get("Start");
	        String end  = map.get("End");
	        String durationInMinutes  = map.get("Duration in minutes");
	        
	        in_category_I_have_a_shift_from_to_with_minutes_total(cateogryName, start, end, durationInMinutes);
		}
	}
}
	
