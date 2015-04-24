package magictester.core;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import magictester.rws.api.APIV3Request;
import magictester.rws.api.APIV3Response;
import magictester.rws.website.pages.Page_API;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import cucumber.api.Scenario;

public abstract class SuperWebsiteAutomator implements iTestManager {
	WebDriver _SeleniumDriver;
	SeleniumAssistant _SeleniumAssistant;
	protected static DesiredCapabilities dCaps;
	
	ChiefManager Chief;
	String TestInstanceName;
	String TestSessionName;
	
	public SuperWebsiteAutomator() {
		this.setChiefManager(ChiefManager.getInstance());
	}

	public SuperWebsiteAutomator(ChiefManager chief) {
		this.setChiefManager(chief);
	}
	
	@Override
	public void setChiefManager(ChiefManager chief) {
		Chief=chief;
		GenericEntry<Scenario, String> NextQueueJob=this.Chief.GetNextQueueBefore();

		while(NextQueueJob!=null){
			this.RunScenarioTagBefore(NextQueueJob.getKey(), NextQueueJob.getValue());
			NextQueueJob=this.Chief.GetNextQueueBefore();
		}

		while(NextQueueJob!=null){
			this.RunScenarioTagAfter(NextQueueJob.getKey(), NextQueueJob.getValue());
			NextQueueJob=this.Chief.GetNextQueueBefore();
		}
	}

	@Override
	public ChiefManager getChiefManager() {
		return Chief;
	}

	public abstract String getBaseURL();
	@Override
	public abstract String getTestAreaName();

	@Override
	public String getTestSessionName() {
		return TestSessionName;
	}

	@Override
	public void setTestSessionName(String testSessionName) {
		this.TestSessionName=testSessionName;
	}

	public WebDriver SeleniumDriver() {
		if(_SeleniumDriver==null) {
			String browser="";
			try { browser=getTestSessionParameter("Browser"); } catch (IOException e) {}
			
			if(browser.trim().equalsIgnoreCase("chrome")) {
				_SeleniumDriver= DriverSetup_Chrome();
			} else if(browser.trim().equalsIgnoreCase("firefox")) {
				_SeleniumDriver= DriverSetup_Firefox();
			} else if(browser.trim().equalsIgnoreCase("PhantomJS")) {
				_SeleniumDriver= DriverSetup_PhantomJSDriver();
			} else if(browser.trim().equalsIgnoreCase("")) {
				_SeleniumDriver= DriverSetup_Chrome();
			} else {
				_SeleniumDriver= DriverSetup_Chrome();
			}
		
			_SeleniumDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			_SeleniumDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			_SeleniumDriver.manage().timeouts().setScriptTimeout(100, TimeUnit.SECONDS);

			Capabilities caps = ((RemoteWebDriver) _SeleniumDriver).getCapabilities();
			String browserName = caps.getBrowserName();
			String browserVersion = caps.getVersion();

			System.out.println("Automated test run. We’re running on " + browserName + " " + browserVersion);
		}
		return _SeleniumDriver;
	}
	
	WebDriver DriverSetup_PhantomJSDriver() {
		dCaps = new DesiredCapabilities();

		String PhantomJSDriverLocation=this.Chief.getRunParameter("RWS Website", "webDriver.phantomJS.Driver");

		if(PhantomJSDriverLocation==null || PhantomJSDriverLocation.length()==0)
			PhantomJSDriverLocation="phantomjs.exe";			
			
		dCaps.setCapability(
				PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				PhantomJSDriverLocation);
		
		// if don't want to use embedded GhostDriver
		// dCaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDriver_PATH_PROPERTY,
		// "");
		dCaps.setJavascriptEnabled(true);
		dCaps.setCapability("takesScreenshot", true);
		WebDriver ret = new PhantomJSDriver(dCaps);
		return ret;
	}

	WebDriver DriverSetup_Chrome() {
		String ChromeDriverLocation=this.Chief.getRunParameter("RWS Website", "webDriver.chrome.Driver");
		System.setProperty("webDriver.chrome.Driver", ChromeDriverLocation);
		System.setProperty("webdriver.chrome.driver", ChromeDriverLocation);
		WebDriver ret = new ChromeDriver();
		return ret;
	}

	WebDriver DriverSetup_Firefox() {
		WebDriver ret = new FirefoxDriver();
		return ret;
	}

	WebDriver DriverSetup_HtmlUnit() {
		WebDriver ret = new HtmlUnitDriver();
		return ret;
	}

	public SeleniumAssistant SeleniumAssistant() {
		if(_SeleniumAssistant==null)
			_SeleniumAssistant= new SeleniumAssistant(SeleniumDriver());
		return _SeleniumAssistant;
	}

	public void OpenHomePage(String subUrl) {
		this.OpenSubURL("");
	}
	
	public void OpenSubURL(String subUrl) {
		SeleniumDriver().get(getBaseURL() + subUrl);
	}

	public void OpenPageByURL(String Url) {
		SeleniumDriver().get(Url);
	}

	public String getTestSessionParameter(String varName) throws IOException {
		return this.Chief.getRunParameter(getTestAreaName(), "TestSession\\" + getTestSessionName(), varName);
	}

	public String getTestProfileParameter(String userProfileName, String varName)  {
		return this.Chief.getRunParameter(getTestAreaName(), "TestProfile\\"    + userProfileName,      varName);
	}

	protected void CloseAllBrowserInstancs() {
		SeleniumDriver().close();
		SeleniumDriver().quit();
	}

	public APIV3Response CallAPI(Page_API page_API, String methodName, APIV3Request requ) {
		String urlRest=requ.GetRETSUrl(page_API.GetUrl(), methodName, requ);
		//SeleniumDriver().get(urlRest); TODO
		String returnedHTML=SeleniumDriver().getPageSource();
		APIV3Response ret  = new APIV3Response();
		ret.LoadHTML(returnedHTML);
		return ret;
	}
}
