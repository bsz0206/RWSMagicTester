@FeatureCloseBrowserOnFail @FeatureCloseBrowserOnSuccess @FeatureScenariosTransactional 

Feature: Holiday Day Type-Shared Calander
	As a manager,
	I like to create an Holiday Day Type and create a calander Item
	

Background:
And In the default test session I am testing application "RWS Website"
And I am using the default test profile

@Runme @StepsTransaction
Scenario: Create a Holiday Day Type Business Oriented
When I login

And I open the account holder menu 
And I select Settings
And I select Day Type from left side bar
And I click on Add a new Day Type button
And I enter "Holiday1" into Name textbox 
And I enter "Purple" into Color textbox 
And I click on Create button
And I select Shared Calander from left side bar
And I click on Add a new Calander Item button
And I enter "Holiday1" into Name textbox 
And I select "2015-07-01" from tha datepicker
And I select "Event" from Kind dropdown 
And I select "Holiday1" from Type dropdown
And I click on "Update" button
