package magictester.core;


public class TestParameter {
	IniFile ini;
	
	static TestParameter testParameter;
	static TestParameter Initialize() {
		if(testParameter==null)
			testParameter= new TestParameter();
		return testParameter;
	}
	
	public TestParameter() {
		ini= new IniFile("TestParameters.ini");
	}

	public static String GetValue(String section, String varName) {
		Initialize();
		return testParameter.ini.getValue(section, varName, "");
	}
}