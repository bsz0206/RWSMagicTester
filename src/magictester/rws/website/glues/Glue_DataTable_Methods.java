package magictester.rws.website.glues;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import magictester.core.SuperGlue;
import magictester.core.iTestManager;
import magictester.rws.website.TestManager_RWSWebSite;
import cucumber.api.DataTable;
import cucumber.api.java.en.When;

public class Glue_DataTable_Methods  extends SuperGlue {
	@Override
	protected iTestManager InitializeTestManager() {
		return new TestManager_RWSWebSite();
	}

	@Override
	protected TestManager_RWSWebSite CurrentTestManager() {
		return (TestManager_RWSWebSite) super.GetCurrentTestManager();
	}

	@When("^I enter the following information in one column vertical table format$")
	public void i_enter_the_following_information_in_one_column_vertical_table_format(List<String> peopleNamesList) throws Throwable {
		System.out.println("\r\n*****************");
		for(String name : peopleNamesList) {
			System.out.println(name);		
		}
	}

	@When("^I enter the following informaton in table without header method List<List<E>>$")
	public void i_enter_the_following_informaton_in_table_without_header(List<List<String>> tableWithoutHeader) throws Throwable {
		System.out.println("\r\n*****************");
		for(List<String> row : tableWithoutHeader) {
			System.out.println("First name=" + row.get(0) + ", Surname=" + row.get(1));		
		}
	}

	@When("^I enter the following information in one row horizontal table format$")
	public void i_enter_the_following_information_in_one_row_horizontal_table_format(List<String> peopleNamesList) throws Throwable {
		System.out.println("\r\n*****************");
		for(String name : peopleNamesList) {
			System.out.println(name);		
		}
	}


	@When("^I enter the following information inline ((?:\\s|\\w|,)+) comma separated values$")
	public void i_enter_the_following_information_inline_comma_separated_values(List<String> peopleNamesList) throws Throwable {
		System.out.println("\r\n*****************");
		for(String name : peopleNamesList) {
			System.out.println(name);		
		}
	}


	@When("^I enter the following information in one row end of step line comma separated values: ((?:\\s|\\w|,)+)$")
	public void i_enter_the_following_information_in_one_row_end_of_step_line_comma_separated_values(List<String> peopleNamesList) throws Throwable {
		System.out.println("\r\n*****************");
		for(String name : peopleNamesList) {
			System.out.println(name);		
		}
	}

	@When("^I enter the following informaton in table with header method DataTable$")
	public void i_enter_the_following_informaton_in_table_with_header_method_DataTable(DataTable dataTable) throws Throwable {
		System.out.println("\r\n*****************");
		for (Map<String, String> map : dataTable.asMaps(String.class, String.class)) {
	        String firstName = map.get("First Name");
	        String lastName  = map.get("Last name");
			System.out.println("First name=" + firstName + ", Surname=" + lastName);		
		}
	}

	class PersonInfo {String firstName; String lastName; }		

	@When("^I enter the following informaton in table with header method List<YourType>$")
	public void i_enter_the_following_informaton_in_table_with_header_method_List_YourType(List<PersonInfo> peopleInfo) throws Throwable {
		System.out.println("\r\n*****************");
		for(PersonInfo pin : peopleInfo) {
			System.out.println("First name=" + pin.firstName + ", Surname=" + pin.lastName);					
		}
	}

	@When("^I enter the following informaton in table with header method Map<K,V>$")
	public void i_enter_the_following_informaton_in_table_with_header_method_Map_K_V(Map<String, Integer> mapList) throws Throwable {
		System.out.println("\r\n*****************");
		for (Entry<String, Integer> entry : mapList.entrySet()) {
			System.out.println("First name=" + entry.getKey() + ", Age=" + entry.getValue() + " years");		
		}
	}

	@When("^I enter the following informaton in table with header method List<Map<K,V>>$")
	public void i_enter_the_following_informaton_in_table_with_header_method_List_Map_K_V(List<Map<String, String>> mapList) throws Throwable {
		System.out.println("\r\n*****************");
		for(Map<String, String> row: mapList) {
			System.out.println("First name=" + row.get("First Name") + ", Surname=" + row.get("Last name"));					
		}
	}

}
