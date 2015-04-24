package magictester.rws.website.glues;

import magictester.core.ChiefManager;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	@Before
	public void Before(Scenario scenario){
		ChiefManager.ResetAllSession();
		ChiefManager.RunScenarioTagBefore(scenario, "");	
	}

	@After
	public void After(Scenario scenario){
		ChiefManager.RunScenarioTagAfter (scenario, "");
	}

	@Before  ("@FeatureScenariosTransactional")
	public void FeatureScenariosTransactional_Before(Scenario scenario){
		ChiefManager.RunScenarioTagBefore(scenario, "FeatureScenariosTransactional");	
	}

	@After   ("@FeatureScenariosTransactional")
	public void FeatureScenariosTransactional_After(Scenario scenario){
		ChiefManager.RunScenarioTagAfter (scenario, "FeatureScenariosTransactional");		
	}

	@Before ("@FeatureCloseBrowserOnFail")
	public void FeatureCloseBrowserOnFail_Before(Scenario scenario){
		ChiefManager.RunScenarioTagBefore(scenario, "FeatureCloseBrowserOnFail");		
	}

	@After  ("@FeatureCloseBrowserOnFail")
	public void FeatureCloseBrowserOnFail_After(Scenario scenario){
		ChiefManager.RunScenarioTagAfter (scenario, "FeatureCloseBrowserOnFail");			
	}

	
	@Before("@FeatureCloseBrowserOnSuccess")
	public void FeatureCloseBrowserOnSuccess_Before(Scenario scenario){
		ChiefManager.RunScenarioTagBefore(scenario, "FeatureCloseBrowserOnSuccess");		
	}

	@After ("@FeatureCloseBrowserOnSuccess")
	public void FeatureCloseBrowserOnSuccess_After(Scenario scenario){
		ChiefManager.RunScenarioTagAfter (scenario, "FeatureCloseBrowserOnSuccess");				
	}

	@Before  ("@StepsTransaction")
	public void StepsTransaction_Before(Scenario scenario){
		ChiefManager.RunScenarioTagBefore(scenario, "StepsTransaction");		
	}

	@After   ("@StepsTransaction")
	public void StepsTransaction_After(Scenario scenario){
		ChiefManager.RunScenarioTagAfter (scenario, "StepsTransaction");			
	}
}
