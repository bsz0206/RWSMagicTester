package magictester.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import magictester.rws.api.TestManager_RWSAPIv3;
import magictester.rws.website.TestManager_RWSWebSite;
import cucumber.api.Scenario;

public class ChiefManager {
    private ChiefManager() {}
    private static final ChiefManager INSTANCE = new ChiefManager();
    private static final ArrayList<GenericEntry<Scenario, String>> TagBeforeQueue=new ArrayList<GenericEntry<Scenario, String>>();
    private static final ArrayList<GenericEntry<Scenario, String>> TagAfterQueue =new ArrayList<GenericEntry<Scenario, String>>();
    private static Map<String, iTestManager> TestManagers = new HashMap<>();
    private static Map<String, Object> registry = new HashMap<>();
    public iTestManager CurrentTestManager;
	
    public static ChiefManager getInstance() {
        return INSTANCE;
    } 
 
    public static iTestManager CurrentTestManager() {
    	return getInstance().CurrentTestManager;
    }
    
    public static Map<String, iTestManager> getAllTestManagers() {
    	return TestManagers;
    }

    public static void ResetAllSession() {
    	TestManagers = new HashMap<>();
    }
    
	public iTestManager SetCurrentTestManagerByAreaName(String testAreaType, String testSession) throws Exception {
		if(testAreaType.equals("RWS Website")) {
			CurrentTestManager = new TestManager_RWSWebSite();
			CurrentTestManager.setTestSessionName(testSession);
			CurrentTestManager.setChiefManager(this);
			TestManagers.putIfAbsent(testSession, CurrentTestManager);
		}else if(testAreaType.equals("RWS API v3")) {
				CurrentTestManager = new TestManager_RWSAPIv3();
				CurrentTestManager.setTestSessionName(testSession);
				CurrentTestManager.setChiefManager(this);
				TestManagers.putIfAbsent(testSession, CurrentTestManager);
		} else {
			throw new Exception("TestManager for '" + testAreaType + "' area not found");
		}
		return CurrentTestManager;
	}
	
	public iTestManager SetCurrentTestManagerByAreaName(iTestManager testManager) throws Exception {
		return SetCurrentTestManagerByAreaName(testManager, MagicTester.DEFAULT_TEST_PROFILE);
	}
	
	public iTestManager SetCurrentTestManagerByAreaName(iTestManager testManager, String testSession) throws Exception {
		CurrentTestManager = testManager;
		CurrentTestManager.setTestSessionName(testSession);
		CurrentTestManager.setChiefManager(this);
		TestManagers.putIfAbsent(testSession, CurrentTestManager);
		return CurrentTestManager;
	}

	public void SwitchCurrentUserSession(String userSession) throws Exception {
		if(CurrentTestManager.getTestSessionName().equals(userSession)) return;
		iTestManager tm = TestManagers.get(userSession);
		if (tm == null) {
			throw new Exception("Test session: " + userSession + " not found.");
		}
		CurrentTestManager=tm;
	}

	public String getRunParameter(String key, String varName) {
		String ret="";
		ret=TestParameter.GetValue(key, varName);
		return ret;
	}
	
	public String getRunParameter(String testAreaType, String TestSession, String varName) {
		String key=testAreaType;
		key+="\\" + TestSession;
		return TestParameter.GetValue(key, varName);
	}

	public String getRunParameter(String testAreaType, String TestSession, String profile, String varName) throws IOException {
		String key=testAreaType;
		key+="\\" + TestSession;
		key+="\\" + profile;
		return TestParameter.GetValue(key, varName);
	}

	public static void RunScenarioTagBefore(Scenario scenario, String tag) {
		if(CurrentTestManager()==null) {
			TagBeforeQueue.add(new GenericEntry<Scenario, String>(scenario, tag));
		} else {
			for(Entry<String, iTestManager> tm : ChiefManager.getAllTestManagers().entrySet()){
				tm.getValue().RunScenarioTagBefore(scenario, tag);
			}
		}
	}

	public static void RunScenarioTagAfter(Scenario scenario, String tag) {
		if(CurrentTestManager()==null) {
			TagAfterQueue.add(new GenericEntry<Scenario, String>(scenario, tag));
		} else {
			for(Entry<String, iTestManager> tm : ChiefManager.getAllTestManagers().entrySet()){
				tm.getValue().RunScenarioTagAfter(scenario, tag);
			}
		}	
	}

	public GenericEntry<Scenario, String> GetNextQueueBefore() {
		return GetNextQueue(ChiefManager.TagBeforeQueue);
	}
	
	public GenericEntry<Scenario, String> GetNextQueueAfter() {
		return GetNextQueue(ChiefManager.TagAfterQueue);
	}

	GenericEntry<Scenario, String> GetNextQueue(ArrayList<GenericEntry<Scenario, String>> queue) {
		GenericEntry<Scenario, String> ret=null;
		if(queue.size()>0) {
			ret=queue.get(0);
			queue.remove(0);
		}
		return ret;
	}

	public Object getSessionProfileValue(String testSessionName, String testProfileName, String valName) {
		String registryValuePath=testSessionName + "/" + testProfileName + "/" + valName;
		return getRegistryValue(registryValuePath);
	}

	public void setSessionProfileValue(String testSessionName,	String testProfileName, String valName, Object value) {
		String registryValuePath=testSessionName + "/" + testProfileName + "/" + valName;
		setRegistryValue(registryValuePath, value);
	}

	public void setRegistryValue(String registryValuePath, Object value) {
		registry.put(registryValuePath, value);
	}

	public Object getRegistryValue(String registryValuePath) {
		return registry.get(registryValuePath);
	}

}
