package magictester.rws.website.glues;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import magictester.core.ArrayComparer;
import magictester.core.SuperGlue;
import magictester.core.UtilityFunctions;
import magictester.core.iTestManager;
import magictester.rws.website.TestManager_RWSWebSite;
import magictester.rws.website.pages.Page_EmployeeManualSettings;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Glue_EmployeeEntryValidation extends SuperGlue {
	@Override
	protected iTestManager InitializeTestManager() {
		return new TestManager_RWSWebSite();
	}

	@Override
	protected TestManager_RWSWebSite CurrentTestManager() {
		return (TestManager_RWSWebSite) super.GetCurrentTestManager();
	}

	public Page_EmployeeManualSettings paEmployeeManualAdd;
	
	public Glue_EmployeeEntryValidation() {
		paEmployeeManualAdd= new Page_EmployeeManualSettings(CurrentTestManager());
	}
	
	@When("^I go to add employee manually page$")   
	public void I_go_to_add_employee_manually_page() throws Throwable {
		paEmployeeManualAdd.OpenPage();
	}

	@When("^I enter the following employee information:$")
	public void i_enter_employee(List<Map<String,String>> enteredFields) throws Throwable {
		for(Entry<String, String> fieldValuePair : enteredFields.get(0).entrySet()) {
			paEmployeeManualAdd.EnterFieldValueByLabel(fieldValuePair.getKey(), fieldValuePair.getValue());
		}
	}

	@When("^I submit$")
	public void i_submit() throws Throwable {
		paEmployeeManualAdd.Submit();
	}
	
	@Then("^the employee should be created successfully$")
	public void the_employee_should_be_created_successfully() throws Throwable {
		if(!paEmployeeManualAdd.EmployeeCreated()) //TOFU
			fail("Failed creating the employee");
	}
	
	@Then    ("^the employee creation should fail$")
	public void the_employee_creation_should_fail() throws Throwable {
		if(paEmployeeManualAdd.EmployeeCreated())
			fail("Unexpectedly could create the employee!!!");
	}

	@Then("^only and only the following fields appear as error:$")
	public void Only_fields_Appear_red(List<String> fieldNameList) throws Throwable {
		List<String> errorFieldNames=paEmployeeManualAdd.GetErrorFieldLabels();
		List<String> rightErrors=ArrayComparer.GetSinglesAtRight(errorFieldNames, fieldNameList);
		List<String>   leftErros=ArrayComparer.GetSinglesAtLeft (errorFieldNames, fieldNameList);
		
		String failErrorDesc="";
		if(rightErrors.size()>0)
			failErrorDesc+="Fields (" + UtilityFunctions.Join(rightErrors, ", ") + ") expected to be in error while they are OK. ";
		
		if(leftErros.size()>0)
			failErrorDesc+="Fields (" + UtilityFunctions.Join(leftErros, ", ") + ") expected to be OK while they are in error.";

		if(failErrorDesc.length()>0)
			fail(failErrorDesc);
		
	}
	
	@Then("^the following fields appear as error:$")
	public void fields_Appear_red(List<String> fieldNameList) throws Throwable {
		List<String> errorFieldNames=ArrayComparer.GetSinglesAtRight(paEmployeeManualAdd.GetErrorFieldLabels(), fieldNameList);
		
		if(errorFieldNames.size()>0)
			fail("Fields (" + UtilityFunctions.Join(errorFieldNames, ", ") + ") expected to be in error while they are not.");
	}

	@Then("^the following fields do NOT appear as error:$")
	public void fields_Appear_NOT_red(List<String> errorFields) throws Throwable {
		List<String> errorFieldNames=ArrayComparer.GetSinglesAtRight(
				paEmployeeManualAdd.GetOKFieldLabels(), 
				errorFields.stream().filter(p -> p!= null).collect(Collectors.toList())
				);
		
		if(errorFieldNames.size()>0)
			fail("Fields (" + UtilityFunctions.Join(errorFieldNames, ", ") + ") expected NOT to be in error while they are.");
	}

	@Then("^fields other than the following fields do NOT appear as error:$")
	public void other_fields_NOT_red(List<String> errorFields) throws Throwable {
		List<String>   leftErros=ArrayComparer.GetSinglesAtLeft (paEmployeeManualAdd.GetErrorFieldLabels(), errorFields);
		
		if(leftErros.size()>0)
			fail("Fields (" + UtilityFunctions.Join(leftErros, ", ") + ") expected to be OK while they are in error.");
	}

}
