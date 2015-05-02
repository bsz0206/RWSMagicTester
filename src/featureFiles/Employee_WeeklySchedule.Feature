@FeatureCloseBrowserOnFail2 @FeatureCloseBrowserOnSuccess2 @FeatureScenariosTransactional

Feature: Validation on new employee entry
	As a employee configuration user,
	I like to receive relavant errors
	when I try to create an employee with incomplete data

Background:
#And SIMULATION TEST CASE 1
#And In the default test session I am testing application "RWS Website"
#And I am using the default test profile
#And In the test session "API" I am testing application "RWS Website"



And In the test session "API" I am testing application "RWS API v3"
And I am using "API" API test profile

@Runme @StepsTransaction
Scenario: API Create a shift
When I login [by APIv3]
And I get all employees [by APIv3]
And I get all employees [by APIv3]


















#When I switch to "API" test session
#And I am using the default test profile
#When I login
#And I get all employees [by WebAPI]
#And I get all employees [by WebAPI]







@Runme @StepsTransaction
Scenario: Scenario 3
And I login
When I go to add employee manually page  
	And I enter the following employee information:
		|Username|Password|Confirm Password|First Name|Surname  |Personal Email|Gender|Termination Date|System Permissions|
		|Kake44  |Abcd1234|Abcd1234        |Kake      |Manoosian|test@mail.test|Male  |2015-04-07      |Supervisor        |
		 #|First Name|
		 #|Jake      |
		 
	And I submit
Then the employee should be created successfully
#Then the employee creation should fail
And I go to Settings > Shift Management

@Runme @StepsTransaction
Scenario: Scenario 2
And In test session "Session_2" I am testing application "RWS Website"
And I login
When I switch to the test session "Session_2"
When I switch to the default test session
And I login
When I go to add employee manually page  
	And I enter the following employee information:
		|Username|Password|Confirm Password|First Name|Surname  |Personal Email|Gender|Termination Date|System Permissions|
		|Kake36  |Abcd1234|Abcd1234        |Kake      |Manoosian|test@mail.test|Male  |2015-04-07      |Supervisor        |
	And I submit
Then the employee should be created successfully
When I switch to the test session "Session_2"
And I go to Settings > Shift Management
When I switch to the default test session
And I go to Settings
And I select Shift Management from left side bar

@Runme @StepsTransaction
Scenario: Scenario 1
When I switch to the test session "Session_2"
	And I login
When I switch to the default test session
	And I login
When I go to add employee manually page  
	And I enter the following employee information:
		|Username|Password|Confirm Password|First Name|Surname  |Personal Email|
		|Kake21  |Abcd1234|Abcd1234        |Kake      |Manoosian|test@mail.test|
	And I submit
Then the employee creation should fail
Then only and only the following fields appear as error:
		|System Permissions|Gender|Termination Date|
When I switch to the test session "Session_2"
