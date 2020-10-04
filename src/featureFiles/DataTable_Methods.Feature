Feature: Testing different methods to load a data table

@Runme
Scenario: Data table method 1
When In the test session "API" I am testing application "RWS API v3"
And I am using "API" API test profile
And I enter the following information in one row end of step line comma separated values: Jake, Kate, John 
And I enter the following information inline Jake, Kate, John comma separated values 
And I enter the following information in table with header method List<YourType>
	|First Name|Last name|
	|Jake      |Jamaica  |
	|Kate      |Mansour  |
	|John      |Doe      |
And I enter the following information in table with header method DataTable
	|First Name|Last name|
	|Jake      |Jamaica  |
	|Kate      |Mansour  |
	|John      |Doe      |
And I enter the following information in table with header method Map<K,V>
	|Jake     |33  |
	|Kate     |23  |
	|John     |56  |
And I enter the following information in table with header method List<Map<K,V>>
	|First Name|Last name|
	|Jake      |Jamaica  |
	|Kate      |Mansour  |
	|John      |Doe      |
And I enter the following information in table without header method List<List<E>>
	|Jake      |Jamaica  |
	|Kate      |Mansour  |
	|John      |Doe      |
And I enter the following information in one column vertical table format
	|Jake|
	|Kate|
	|Johnaton|
And I enter the following information in one row horizontal table format
	|Jake|Kate|John|
