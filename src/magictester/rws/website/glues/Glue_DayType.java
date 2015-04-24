package magictester.rws.website.glues;

import magictester.core.SuperGlue;
import magictester.core.iTestManager;
import magictester.rws.website.TestManager_RWSWebSite;

public class Glue_DayType  extends SuperGlue {
	@Override
	protected iTestManager InitializeTestManager() {
		return new TestManager_RWSWebSite();
	}

	@Override
	protected TestManager_RWSWebSite CurrentTestManager() {
		return (TestManager_RWSWebSite) super.GetCurrentTestManager();
	}

	
}
