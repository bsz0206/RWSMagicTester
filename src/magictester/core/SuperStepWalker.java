package magictester.core;


public abstract class SuperStepWalker {
	SuperWebsiteAutomator websiteAutomator;
	
	public SuperStepWalker(SuperWebsiteAutomator testManager_RWSWebSite) {
		SetCurrentTestManager(testManager_RWSWebSite);
	}

	public void SetCurrentTestManager(SuperWebsiteAutomator websiteAutomator) {
		this.websiteAutomator=websiteAutomator;
	}

	protected SuperWebsiteAutomator CurrentWebsiteAutomator() {
		return websiteAutomator;
	}
}
