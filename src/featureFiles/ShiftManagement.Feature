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

#
And I open the account holder menu 
And I select Settings
And I select Shift Management from left side bar
And I click on Add a new Shift button
#NOT VERY GOOD:  And I create a Category named "Test"         ## see the two lines below 
And I select to Create New Category from the Category dropdown
And I enter "Test" as new Cateogry name
And I press OK to create a new Cateogry 
And I select "Test" from the Category dropdown
And I enter "6:00" into Shift Start textbox 
And I enter "14:00" into Shift End textbox 
And I click on Create Shift button
#Then I have a catorry name "Test"
#And in category "Test" I have a shift "6:00" to "14:00" with "8" hours total

