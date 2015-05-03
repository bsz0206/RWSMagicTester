@FeatureCloseBrowserOnFail2 @FeatureCloseBrowserOnSuccess2 @FeatureScenariosTransactional 

Feature: Shift Management
	As a manager,
	I like to create and archive shifts
	

Background:
And In the default test session I am testing application "RWS Website"
And I am using the default test profile
And In the test session "API" I am testing application "RWS Website"

@Runme @StepsTransaction
Scenario: Create a shift Business Oriented
When I login
When I create a shift from "6:10" to "14:10"

@RunmeEEF @StepsTransaction
Scenario: Create a shift
When I switch to the default test session
And I login
And I open the account holder menu 
And I select Settings
And I select Shift Management from left side bar
And I click on Add a new Shift button
And I select to Create New Category from the Category dropdown
And I enter "Night" as new Cateogry name
And I press OK to create a new Cateogry 
And I select "Night" from the Category dropdown
And I enter "22:00" into Shift Start textbox 
And I enter "06:00" into Shift End textbox 
And I click on Create Shift button
Then I have a category named "Night"
And in category "Night" I have a shift "22:00" to "06:00" with "8" hours total



Scenario: Create a shift (BDT)
When I create a shift from "10:35" to "17:35" with "7" hours total time in category "Mid-Day"
Then in category "Mid-Day", a "10:35" to "17:35" shift with "420" minutes duration exists



Scenario: Create a shift (BDT; Data Table)
When I create a the following shift:
   |Cateogry Name|Start|End  |Duration in minutes|
   |Nights       |14:30|23:30|540                |
Then the following shift exists:
   |Cateogry Name|Start|End  |Duration in minutes|
   |Nights       |14:30|23:30|540                |




Scenario: Create a shift (BDT; Data Table; Multi row)
When I create a the following shifts:
   |Cateogry Name|Start|End  |Duration in minutes|
   |Nights       |14:30|23:30|540                |
   |Days         |07:30|15:30|480                |
Then the following shifts exists:
   |Cateogry Name|Start|End  |Duration in minutes|
   |Nights       |14:30|23:30|540                |
   |Days         |07:30|15:30|480                |
 