package magictester.rws.api;

import java.io.IOException;

import magictester.core.ChiefManager;
import magictester.core.SuperWebsiteAutomator;
import cucumber.api.Scenario;

public class TestManager_RWSAPIv3 extends SuperWebsiteAutomator {
	private String testSessionName;
	private String testProfileName;
	private ChiefManager Chief;

	@Override
	public String getTestAreaName() {
		return "RWS API V3";
	}

	@Override
	public String getTestSessionName() {
		return testSessionName;
	}

	@Override
	public void setTestSessionName(String testSessionName) {
		this.testSessionName=testSessionName;
	}

	@Override
	public String getTestProfileName() {
		return testProfileName;
	}

	@Override
	public void setTestProfileName(String testProfileName) {
		this.testProfileName=testProfileName;
	}

	@Override
	public ChiefManager getChiefManager() {
		return Chief;
	}

	@Override
	public String getBaseURL() {
		return null;
	}

	@Override
	public void setChiefManager(ChiefManager chief) {
		this.Chief=chief;
	}

	@Override
	public String[] getGluePackages() {
		String[] ret= new String[1];
		ret[0]="magictester.rws.apiv3.glues";
		return ret;
	}

	@Override
	public void RunScenarioTagAfter(Scenario scenario, String tag) {
		//System.out.println("before_" + tag + "=" + scenario.getName());
	}

	@Override
	public void RunScenarioTagBefore(Scenario scenario, String tag) {
		//System.out.println("after_" + tag + "=" + scenario.getName());
	}

	public String getTestSessionParameter(String varName) throws IOException {
		return this.Chief.getRunParameter(getTestAreaName(), "TestSession\\" + getTestSessionName(), varName);
	}

	public String getTestProfileParameter(String varName) {
				return this.Chief.getRunParameter(getTestAreaName(), "TestProfile\\"    + this.testProfileName,      varName);
	}

	public void LoadTestProfile(String testProfileName) {
		this.testProfileName=testProfileName;
	}

}
