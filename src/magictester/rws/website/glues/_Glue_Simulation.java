package magictester.rws.website.glues;

import magictester.core.SuperGlue;
import magictester.core.iTestManager;
import magictester.rws.website.TestManager_RWSWebSite;
import cucumber.api.java.en.Given;

public class _Glue_Simulation extends SuperGlue {
	@Override
	protected iTestManager InitializeTestManager() {
		return new TestManager_RWSWebSite();
	}

	@Override
	protected TestManager_RWSWebSite CurrentTestManager() {
		return (TestManager_RWSWebSite) super.GetCurrentTestManager();
	}

	@Given("^SIMULATION TEST CASE (\\d+)$")
	public void testcase(int arg1) throws Throwable {
		//String flashesXML = String.join("\n", Files.readAllLines(Paths.get("Testing/TestData/created_1.xml")));
		//new Page_TopMainFrame(null).getActionResultsTOFU(flashesXML);
		//throw new Exception();
	}
}
