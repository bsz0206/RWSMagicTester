package magictester.core;

public abstract class SuperGlue {
	protected abstract iTestManager CurrentTestManager();
	protected abstract iTestManager InitializeTestManager();
	
	public iTestManager GetCurrentTestManager() {
		iTestManager ret=ChiefManager.CurrentTestManager();
		if(ret==null) {
			try {
				ret=ChiefManager.getInstance().SetCurrentTestManagerByAreaName(InitializeTestManager());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
}
