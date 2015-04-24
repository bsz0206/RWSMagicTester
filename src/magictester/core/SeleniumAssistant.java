package magictester.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumAssistant {
	public WebDriver SeleniumDriver;
	
	public SeleniumAssistant(WebDriver mainDriver) {
		this.SeleniumDriver=mainDriver;
	}

	public void CloseBrowser() {
		SeleniumDriver.close();
	}

	public void QuitDrivers() {
		SeleniumDriver.quit();
	}

	public void OpenPage(String url) {
		SeleniumDriver.get(url);
		//for (int lop = 0; lop < 3 && !WaitforPageLoad(); lop++) {}
	}

	public WebElement FindElement(By by) {
		WebElement ret=null;
		boolean found=false;
		//Exception lastException=null;
		for(int lop=0; lop<30 && !found; lop++) {
			try {
				ret=SeleniumFindElement(by);
				found=true;
			} catch (Exception e) {
				//lastException=e;
			}
		}
		if(!found) {
		}
		return ret;
	}

	public WebElement FindElementByID(String elementID) {
		WebElement ret=null;
		boolean found=false;
		//Exception lastException=null;
		for(int lop=0; lop<30 && !found; lop++) {
			try {
				ret=SeleniumFindElement(By.id(elementID));
				found=true;
			} catch (Exception e) {
				//lastException=e;
			}
		}
		if(!found) {
		}
		return ret;
	}
	
	public String Textbox_GetText(String textboxElementID) {
		WebElement textbox=FindElementByID(textboxElementID);
		return textbox.getAttribute("value");
	}

	public double Textbox_GetTextAsDouble(String textboxElementID) throws Exception {
		String amount = Textbox_GetText(textboxElementID);
		if (!UtilityFunctions.isNumeric(amount)) {
			throw new Exception("the current value of field " + "elementLocationDescriber" + " is not numeric");
		}
		return Double.parseDouble(amount);
	}

	public void DropDown_SelectText(String dropDownID, String value) {
		Select seleniumObject=new Select(FindElementByID(dropDownID));
		seleniumObject.selectByVisibleText(value);
	}

	public String DropDown_GetSelectedText(String dropDownID) {
		return Textbox_GetText(dropDownID);
	}

	public void Button_ByName_Click(String name) {
		SeleniumFindElement(By.name(name)).click();
	}

	public boolean WaitForPageTitle(String titleMatchStringRegex, int timeoutSeconds) throws InterruptedException {
	    for (int second = 0; second<2*timeoutSeconds; second++) {
	    	try {
	    		if(SeleniumDriver.getTitle().matches(titleMatchStringRegex)) { return true; }
	    	} catch(Exception e) { }
	    	UtilityFunctions.Sleep(500);
	    }
	    return false;
	}

	public String GetAttributeByID(String fieldID, String attributeName) {
		return SeleniumFindElement(By.id(fieldID)).getAttribute(attributeName);
	}

	public void TextBox_SetText(String textboxElementID, String value) {
		SetText(By.id(textboxElementID), value);
	}

	public void SetText(By by, String text) {
		WebElement textbox=SeleniumFindElement(by);
		textbox.clear();
		textbox.sendKeys(text);	
	}
	
	WebElement SeleniumFindElement(By by) {
		WebElement we = null;
		we=SeleniumDriver.findElement(by);
		return we;
	}

	public Boolean WaitUntilBothHappens (
			ExpectedCondition<WebElement> cond1,
			ExpectedCondition<WebElement> cond2
			){
		return WaitUntilBothHappens(cond1, cond2, 30000);
	}
	
	private boolean WaitUntilBothHappens (
			ExpectedCondition<WebElement> cond1,
			ExpectedCondition<WebElement> cond2,
			long MaxMiliSeconds
		) {
		
		List<ExpectedCondition<WebElement>> conditions=new ArrayList<ExpectedCondition<WebElement>>();
		conditions.add(cond1);
		conditions.add(cond2);
		return WaitForConditions(conditions, 2, 2, MaxMiliSeconds);
	}
	
	public Boolean WaitUntilEitherHappens (
			ExpectedCondition<WebElement> cond1,
			ExpectedCondition<WebElement> cond2
			){
		return WaitUntilEitherHappens(cond1, cond2, 30000);
	}
	
	private boolean WaitUntilEitherHappens (
			ExpectedCondition<WebElement> cond1,
			ExpectedCondition<WebElement> cond2,
			long MaxMiliSeconds
		) {
		
		List<ExpectedCondition<WebElement>> conditions=new ArrayList<ExpectedCondition<WebElement>>();
		conditions.add(cond1);
		conditions.add(cond2);
		return WaitForConditions(conditions, 1, 2, MaxMiliSeconds);
	}
	
	public Boolean WaitForConditions (
			List<ExpectedCondition<WebElement>> conditions,
			int minPositive, int maxPositive,
			long MaxMiliSeconds
			) {
		
		long onceWaitMS=100;
		long startTime = System.currentTimeMillis();
		
		while(System.currentTimeMillis() - startTime < MaxMiliSeconds) {
			if(WaitForConditionsOnce(conditions, minPositive, maxPositive, onceWaitMS))
				return true;
		}
		
		return false;
	}

	public Boolean WaitForConditionsOnce (
			List<ExpectedCondition<WebElement>> conditions,
			int minPositive, int maxPositive,
			Long timeoutPerWaitMs
			) {

		Boolean ret=true;

		int min=minPositive;
		int max=maxPositive;
		int positive=0;
		int lop=0;
		int count=0;

		for(ExpectedCondition<WebElement> condition : conditions) {
			if(condition!=null) {
				count++;
			} else {
				min--;
				max--;
			}
		}
		
		for(ExpectedCondition<WebElement> condition : conditions) {
			if(condition==null)
				continue;

			lop++;

			if(WaitForCondition(condition, timeoutPerWaitMs))
				positive++;

			int optimisitcMaxPositive=positive + count - lop;
			int pessimisticMinPositive=positive;

			if(optimisitcMaxPositive < min) {
				ret=false;
				break;
			}
			
			if(pessimisticMinPositive > max) {
				ret=false;
				break;
			}			
		}
		
		return ret;
	}

	public Boolean WaitForConditionSeconds(ExpectedCondition<WebElement> condition, long timeoutSeconds) {
		return WaitForCondition(condition, timeoutSeconds * 1000);
	}
	
	public Boolean WaitForCondition(ExpectedCondition<WebElement> condition, long timeoutMs) {
		long timeoutSecs=timeoutMs/1000;
		if(timeoutSecs==0)
			timeoutSecs=1;
		WebDriverWait waiter = new WebDriverWait(SeleniumDriver, timeoutSecs);
		waiter.pollingEvery(50, TimeUnit.MILLISECONDS);
		waiter.withTimeout(50, TimeUnit.MILLISECONDS);
		
		Boolean ret=false;
		try { 
			waiter.until(condition); //TODO waits more than expected
			ret=true; 
			} 
		catch(Exception e) 
		{ 
			return false; 
		}
		return ret;
	}

	public void ClickByCoordinate(By elementBy, int rightPercentage, int downPercentage) {
		WebElement element = SeleniumDriver.findElement(elementBy);
		Actions build = new Actions(SeleniumDriver);
		int X=element.getSize().width*rightPercentage/100;
		int Y=element.getSize().height*downPercentage/100;
		build.moveToElement(element, X, Y).click().build().perform();
	}

	public Boolean SaveHTMLIfChanged(String prefixPath, String newHTML, String oldHTML) {
		if(newHTML.equals(oldHTML))
			return false;
		File f = new File(prefixPath + new SimpleDateFormat("yyyyMMdd-hh.mm.ss.SSS'.txt'").format(new Date()) + ".html");
		try {
			FileWriter writer = new FileWriter(f);
			writer.append(newHTML);
			writer.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void SaveHTMLChanges(String prefixPath, int forSecs, int DelayMiliSecs) {
		long startTime = System.currentTimeMillis();
		String previoushtml="";
		String html="";
		//Date lastOne=new Date();
		while((System.currentTimeMillis()-startTime) < forSecs*1000) {
			try {
				previoushtml=html;
				html=SeleniumDriver.getPageSource();
				if(SaveHTMLIfChanged(prefixPath, html, previoushtml)) {
					//lastOne=new Date();
					continue;
				} /*else {
					if(previoushtml.length()>0) {
						File f = new File(path + new SimpleDateFormat("yyyyMMdd-hh.mm.ss.SSS'.txt'").format(lastOne) + ".html");
						FileWriter writer = new FileWriter(f);
						writer.append(previoushtml);
						writer.close();
					}
				}*/
				UtilityFunctions.Sleep(DelayMiliSecs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public WebElement WaitForElement(By by, int MaxWaitMS, int waitBetweenChecksMS) {
		WebElement ret=null;
		long startTime = System.currentTimeMillis();
		while((System.currentTimeMillis()-startTime) < MaxWaitMS) {
			List<WebElement> elements=SeleniumDriver.findElements(by);

			if(elements.size()>0) {
				ret=elements.get(0);
				break;
			}
			UtilityFunctions.Sleep(waitBetweenChecksMS);
		}
		return ret;
	}

	public void WaitForElementToClick(By by) throws Exception {
		WaitForElementToClick(by, 10000);
	}
	
	public void WaitForElementToClick(By by, long timeout) throws Exception {
		List<ExpectedCondition<WebElement>> itemExpectations=new ArrayList<ExpectedCondition<WebElement>>();
		itemExpectations.add(ExpectedConditions.presenceOfElementLocated(by));
		itemExpectations.add(ExpectedConditions.visibilityOfElementLocated(by));
		itemExpectations.add(ExpectedConditions.elementToBeClickable(by));
		
		if(this.WaitForConditions(itemExpectations, 3, 3, timeout))	{
			WebElement we = SeleniumFindElement(by);
			we.click();
		}
	}

	public void ClickFailSafe(By by) {
		WebElement el=SeleniumDriver.findElement(by);
		try { el.click(); return; }  catch (Exception ex) { }
		try { 
			Actions actions = new Actions(SeleniumDriver);
			actions.moveToElement(el).perform();
			((JavascriptExecutor)SeleniumDriver).executeScript("arguments[0].scrollIntoView();", el);
			el.click(); 
			return; 
		} catch (Exception ex) {}
	}
	
}
