package magictester.rws.website;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import magictester.core.PageField;
import magictester.core.SeleniumAssistant;
import magictester.core.UtilityFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RWSCommonActionResult {
	Document flashesXML;
	Node FlashesNode;
	public SeleniumAssistant SeleniumAssistant;
	public WebDriver SeleniumDriver;
	public LinkedList<PageField> PageFieldList; 
	public List<RWSCommonActionResultErrorBox> RWSCommonActionResultErrorBoxList;
	public List<RWSCommonActionResultWarningBox> RWSCommonActionResultWarningBoxList;
	public List<RWSCommonActionResultNoticeBox> RWSCommonActionResultNoticeBoxList;

	private RWSCommonActionResult() {
		PageFieldList = new LinkedList<PageField>();
		RWSCommonActionResultErrorBoxList=new ArrayList<RWSCommonActionResultErrorBox>();
		RWSCommonActionResultWarningBoxList=new ArrayList<RWSCommonActionResultWarningBox>();
		RWSCommonActionResultNoticeBoxList=new ArrayList<RWSCommonActionResultNoticeBox>();
		
		PageField_Add("Flashes", By.xpath("//*[@class=\"flashes\"]"));

		PageField_Add("ErrorBox",        By.xpath("//div[@class='errorExplanation']"));
		PageField_Add("WarningBox",      By.xpath("//div[@class='item warning']"));

		PageField_Add("ErrorBoxClose",   By.xpath("//div[@class='errorExplanation']//*[@class='close']"));
		PageField_Add("WarningBoxClose", By.xpath("//div[@class='item warning']//*[@class='close']"));
		
		//PageField_Add("errorExplanation", By.xpath("(//div[@id='errorExplanation']/span)[2]"));
		//PageField_Add("errorExplanation", By.cssSelector("#errorExplanation > span.close"));
		//PageField_Add("InstallChromeError", By.cssSelector("button.ui-dialog-titlebar-close"));	
	}
	
	public RWSCommonActionResult(WebDriver SeleniumDriver, SeleniumAssistant SeleniumAssistant) {
		this();
		this.SeleniumDriver=SeleniumDriver;
		this.SeleniumAssistant=SeleniumAssistant;
	}

	class AlertDialogBox {
		WebElement WebElement=null;
		
		AlertDialogBox(WebElement we) {
			this.WebElement=we;
		}
		
		int CompareWith(AlertDialogBox challenge){
			int ret=0;
			if(this.Y() < challenge.Y()) ret=+1;
			if(this.Y() > challenge.Y()) ret=-1;
			return ret;
		}
		
		int LastY;
		private int cachedY;
		int Y() {
			this.cachedY=WebElement.getLocation().y;
			return cachedY;
		}
		
		boolean WaitForAppearance() {
			List<ExpectedCondition<WebElement>> itemExpectations = new ArrayList<ExpectedCondition<WebElement>>();
			itemExpectations.add(ExpectedConditions.visibilityOf(this.WebElement));
			itemExpectations.add(ExpectedConditions.elementToBeClickable(this.WebElement));
			return SeleniumAssistant.WaitForConditions(itemExpectations, 2, 2, 10000); 
		}

		void ClickOnXbutton() {
			int Xoffset=-13;
			int Yoffset=13;
			int aroundY=2;
			int aroundX=2;

			Actions build = new Actions(SeleniumDriver);
			
			int X=Xoffset;
			int Y=Yoffset;;	
			if(Xoffset<0)			X+=this.WebElement.getSize().getWidth();
			if(Yoffset<0)			Y+=this.WebElement.getSize().getHeight();
			
			for(int diffX=-aroundX; diffX < aroundX; diffX++)
				for(int diffY=-aroundY; diffY < aroundY; diffY++) {
					for(int retry=0; retry < 2; retry++) {
						try {
							build.moveToElement(this.WebElement, X, Y).click().build().perform();
							break;
						} catch (StaleElementReferenceException ex) {
							UtilityFunctions.Sleep(10);
						}
					}
				}
		}	
}
	
	public void WaitForAppearanceThenCloseAllResultDialogBoxes() throws Exception {
		By by=null;
		
		List<AlertDialogBox> list=new ArrayList<AlertDialogBox>();

		if(WarningReported()) {
			by=GetFieldByCode("WarningBox").By;
			for(Object ob : SeleniumDriver.findElements(by).toArray()) {
				AlertDialogBox sy=new AlertDialogBox((WebElement) ob);
				list.add(sy);
				sy.WaitForAppearance();
			}
		}

		if(ErrorReported()) {
			by=GetFieldByCode("ErrorBox").By;
			for(Object ob : SeleniumDriver.findElements(by).toArray()) {
				AlertDialogBox sy=new AlertDialogBox((WebElement) ob);
				list.add(sy);
				sy.WaitForAppearance();
			}
		}
		
		while(true) {
			boolean shouldBreak=true;
			for(AlertDialogBox sy : list) {	sy.LastY=sy.Y(); }
			for(AlertDialogBox sy : list) {	
				UtilityFunctions.Sleep(50);
				if(sy.LastY!=sy.Y())  
					shouldBreak=false; 
			}
			if(shouldBreak) break;
		}
		
		Collections.sort(list, new Comparator<AlertDialogBox>() {
			@Override
			public int compare(AlertDialogBox sy1, AlertDialogBox sy2) {	return sy1.CompareWith(sy2); }
		});

		for(AlertDialogBox sy : list) {
			sy.ClickOnXbutton();
		}	
	}

	void PageField_Add(String code, By by) {
		PageFieldList.add(new PageField(code, by));	
	}
	
	public void getResults() {
		getResults(10000, 5000, 100, 2000, 5);
	}
	public void getResults(int MaxWaitMS, int WaitForFirstMS, int waitBetweenChecksMS, int RecheckForMiliSeconds, int SimilarCountToPass) {
		String flashesHTML=getFlashesHTML(MaxWaitMS, WaitForFirstMS, waitBetweenChecksMS, RecheckForMiliSeconds, SimilarCountToPass);
		if(flashesHTML!=null && flashesHTML.length()>0) {
			this.LoadHTML(flashesHTML);		
		}
		
		System.out.println(this.Print());
	}

	protected PageField GetFieldByCode(String code) {
		List<PageField> matchedFields = this.PageFieldList.stream().filter(p -> p.Code.equals(code)).collect(Collectors.toList());
		return matchedFields.get(0);	
	}

	public String getFlashesHTML(int MaxWaitMS, int WaitForFirstMS, int waitBetweenChecksMS, int RecheckForMiliSeconds, int SimilarCountToPass) {
		String ret=null;

		String bestHTML="";
		String HTML="";
		By flashesBy=GetFieldByCode("Flashes").By;
		
		long startTime = System.currentTimeMillis();
		int SameSizeNonZeroCount=0;
		
		bestHTML=getWebElementHTML(SeleniumAssistant.WaitForElement(flashesBy, WaitForFirstMS, waitBetweenChecksMS));
		while((System.currentTimeMillis()-startTime) < MaxWaitMS) {
			HTML=getWebElementHTML(SeleniumAssistant.WaitForElement(flashesBy, RecheckForMiliSeconds, waitBetweenChecksMS));
			if(HTML.length() == bestHTML.length() && bestHTML.length() > 0)
				SameSizeNonZeroCount++;
			
			if(HTML.length() > bestHTML.length()) 
				bestHTML=HTML;
			
			if(SameSizeNonZeroCount>=SimilarCountToPass)
				break;
			
			if(HTML.length() < bestHTML.length() && SameSizeNonZeroCount>=SimilarCountToPass)
				if(HTML.length()>0)
					break;
		}
		
		ret=bestHTML;
		return ret;
	}
	
	String getWebElementHTML(WebElement we) {
		String ret="";
		try {
		if(we!=null)
			ret=we.getAttribute("outerHTML");
		} catch (Exception ex) {
			
		}
		return ret;
	}
	
	public String Print() {
		String ret="";
		if(RWSCommonActionResultErrorBoxList.size()>0){
			ret+="Errors boxes:\n";
			for(RWSCommonActionResultErrorBox box : RWSCommonActionResultErrorBoxList) {
				ret+="\tErrors box:\n";
				ret+=fixAllLines("\t\t", box.Print(), "\n");
			}
		}

		if(RWSCommonActionResultWarningBoxList.size()>0){
			ret+="Warning boxes:\n";
			for(RWSCommonActionResultWarningBox box : RWSCommonActionResultWarningBoxList) {
				ret+="\tWarning box:\n";
				ret+=fixAllLines("\t\t", box.Print(), "\n");
			}
		}

		if(RWSCommonActionResultNoticeBoxList.size()>0){
			ret+="Notice boxes:\n";
			for(RWSCommonActionResultNoticeBox box : RWSCommonActionResultNoticeBoxList) {
				ret+="\tNotice box:\n";
				ret+=fixAllLines("\t\t", box.Print(), "\n");
			}
		}

		return ret;
	}

	public void LoadHTML(String flashesHTML) {
		flashesXML=UtilityFunctions.loadXMLFromString(flashesHTML);
		FlashesNode=flashesXML.getFirstChild();
		
		NodeList nodeList;
		
		nodeList= UtilityFunctions.GetSubNodeSetByXPath(FlashesNode, "//div[@class=\"errorExplanation\"]");
		if(nodeList!=null)
		    for (int i = 0; i < nodeList.getLength(); i++)
		    	RWSCommonActionResultErrorBoxList.add(new RWSCommonActionResultErrorBox(nodeList.item(i)));
		
		nodeList= UtilityFunctions.GetSubNodeSetByXPath(FlashesNode, "//div[@class=\"item warning\"]");
		if(nodeList!=null)
		    for (int i = 0; i < nodeList.getLength(); i++)
		    	RWSCommonActionResultWarningBoxList.add(new RWSCommonActionResultWarningBox(nodeList.item(i)));

		nodeList= UtilityFunctions.GetSubNodeSetByXPath(FlashesNode, "//div[contains(@class, \"ui-dialog-titlebar\")]");
		if(nodeList!=null)
		    for (int i = 0; i < nodeList.getLength(); i++)
		    	RWSCommonActionResultNoticeBoxList.add(new RWSCommonActionResultNoticeBox(nodeList.item(i)));
	}

	String fixAllLines(String prefix, String source, String suffix) {
		String ret="";
		String lines[] = source.split("\\r?\\n");
		for(String line: lines) {
			ret+=prefix + line + suffix;
		}
		return ret;
	}
	
	public class RWSCommonActionResultNoticeBox {
		public Node itemdialogbar;
		public String Title;
	
		public RWSCommonActionResultNoticeBox(Node itemdialogbar) {
			this.itemdialogbar=itemdialogbar;
			Title=UtilityFunctions.GetSubNodeByXPath(itemdialogbar, "//*[@class=\"ui-dialog-title\"]").getTextContent();
		}
	
		public String Print() {
			String ret="";
			
			if(Title!=null && Title.trim().length()>0)
				ret+=Title.trim() + "\n";
			
			return ret;
		}
	}
	
	public class RWSCommonActionResultErrorBox {
		public Node ErrorExplanationNode;
		public String Title;
		public String SubTitle;
		public List<RWSCommonActionResultErrorItem> RWSCommonActionResultErrorItems;
		
		public RWSCommonActionResultErrorBox(Node errorExplanationNode) {
			RWSCommonActionResultErrorItems=new ArrayList<RWSCommonActionResultErrorItem>();
			this.ErrorExplanationNode=errorExplanationNode;
			Title=UtilityFunctions.GetSubNodeByXPath(errorExplanationNode, "h2").getTextContent();
			SubTitle=UtilityFunctions.GetSubNodeByXPath(errorExplanationNode, "p").getTextContent();
			NodeList list = ((Element)errorExplanationNode).getElementsByTagName("ul").item(0).getChildNodes();
			list.getLength();
			
			for(int n = 0; n<list.getLength(); n++) {
				RWSCommonActionResultErrorItems.add(new RWSCommonActionResultErrorItem((Element)list.item(n)));
			}
		}
		
		public String Print() {
			String ret="Reported alerts:\n";
			
			if(Title!=null && Title.trim().length()>0)
				ret+=Title.trim() + "\n";
	
			if(SubTitle!=null && SubTitle.trim().length()>0)
				ret+=SubTitle.trim() + "\n";
			
			for(RWSCommonActionResultErrorItem item : RWSCommonActionResultErrorItems)
				ret+=fixAllLines("\t", item.Print(), "\n");
					
			return ret;
		}
	
		public class RWSCommonActionResultErrorItem {
			public Node liNode;
			public String Title;
			public RWSCommonActionResultErrorItem(Element li) {
				liNode=li;
				Title=li.getTextContent();
			}
			public String Print() {
				String ret="";
				
				if(Title!=null && Title.trim().length()>0)
					ret+=Title.trim() + "\n";
				
				return ret;
			}	
		}
	}
	
	public class RWSCommonActionResultWarningBox {
		public Node ItemWarningNode;
		public String Title;
		
		public RWSCommonActionResultWarningBox(Node itemWarningNode) {
			this.ItemWarningNode=itemWarningNode;
			Title=itemWarningNode.getTextContent();
		}
	
		public String Print() {
			String ret="";
			
			if(Title!=null && Title.trim().length()>0)
				ret+=Title.trim() + "\n";
				
			return ret;
		}
	}

	public boolean ErrorReported() {
		return !this.RWSCommonActionResultErrorBoxList.isEmpty();
	}

	private boolean WarningReported() {
		return !this.RWSCommonActionResultWarningBoxList.isEmpty();
	}
}
