package magictester.rws;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)

@CucumberOptions(
	features = {
		"src/featureFiles/ShiftManagement.Feature",
		"src/featureFiles/Employee_WeeklySchedule.feature",
		"src/featureFiles/DataTable_Methods.feature"
		//"src/featureFiles/Employee_Entry_Validation.feature"
		//"classpath:features/change_order/requests/vendor_request_addition.feature"
		},
	glue = {
		"magictester.core.glues",
		"magictester.rws.website.glues",
		"magictester.rws.apiv3.glues"
		},
	plugin = {
		//"pretty",
		//"html:target/cucumber-html-report",
		"html:target/results",
		"junit:target/cucumber-junit-report/allcukes.xml"
		},
	tags = {
			"@RunmeBSZ"
		}
)

public class CukeRunner_BSZ {
}
