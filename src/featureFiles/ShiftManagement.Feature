@FeatureCloseBrowserOnFail2 @FeatureCloseBrowserOnSuccess2 @FeatureScenariosTransactional 

Feature: Shift Management
	As a manager,
	I like to create and archive shifts
	

Background:
And In the default test session I am testing application "RWS Website"
And I am using the default test profile
#And In the test session "API" I am testing application "RWS Website"
When I login

@Runme @StepsTransaction
Scenario: Create a shift Business Oriented
When I create a shift from "6:10" to "14:10"

@Runme @StepsTransaction
Scenario: Create a shift
When I switch to the default test session


#
And I open the account holder menu 
And I select Settings
And I select Shift Management from left side bar
And I click on Add a new Shift button
#NOT VERY GOOD:  And I create a Category named "Test"         ## see the two lines below 
And I select to Create New Category from the Category dropdown
And I enter "Night" as new Cateogry name
And I press OK to create a new Cateogry 
And I select "Night" from the Category dropdown
And I enter "22:00" into Shift Start textbox 
And I enter "06:00" into Shift End textbox 
And I click on Create Shift button
Then I have a category named "Night"
And in category "Night" I have a shift "22:00" to "06:00" with "8" hours total

@Runme
Scenario: Create a shift BDT
When I create a shift which starts at "10:35" and ends at "17:35" in category "Mid-Day"
Then in category "Mid-Day" I have a shift from "10:35" to "17:35" with "420" minutes total


@Runme
Scenario: Create a shift (BDT; Data Table)
When I create the following shifts:
   |Cateogry Name|Start|End  |
   |Nights       |14:30|23:30|
Then the following shifts exists:
   |Cateogry Name|Start|End  |Duration in minutes|
   |Nights       |14:30|23:30|540                |



@RunmeEEF
Scenario: Create a shift (BDT; Data Table; Multi row)
When I create the following shifts:
   |Cateogry Name|Start|End  |
   |Nights       |14:30|23:45|
   |Days         |07:30|15:30|
Then the following shifts exists:
   |Cateogry Name|Start|End  |Duration in minutes|
   |Nights       |14:30|23:45|555                |
   |Days         |07:30|15:30|480                |