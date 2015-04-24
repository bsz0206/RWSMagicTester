package magictester.core;

import cucumber.api.Scenario;

public interface iTestManager {
	String getTestAreaName();
	
	String getTestSessionName();
	void   setTestSessionName(String userSessionName);

	String getTestProfileName();
	void   setTestProfileName(String userProfileName);

	ChiefManager getChiefManager();
	void         setChiefManager(ChiefManager chief);

	String[] getGluePackages();

	void RunScenarioTagAfter(Scenario scenario, String tag);

	void RunScenarioTagBefore(Scenario scenario, String tag);
}
