package magictester.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class SuperWebPageAutomator {
	protected SuperWebsiteAutomator WebsiteAutomator;
	public SeleniumAssistant SeleniumAssistant;
	public WebDriver SeleniumDriver;
	public LinkedList<PageField> PageFieldList; 
	public abstract String GetUrl();
	
	public SuperWebPageAutomator(SuperWebsiteAutomator websiteAutomator) {
		if(websiteAutomator==null) return;
		this.WebsiteAutomator=websiteAutomator;
		this.SeleniumDriver=websiteAutomator.SeleniumDriver();
		this.SeleniumAssistant=websiteAutomator.SeleniumAssistant();
		PageFieldList = new LinkedList<PageField>();
	}

	public void PageField_Add(String code, By by) {
		PageFieldList.add(new PageField(code, by));	
	}
	
	public enum ControlType { textbox, dropdown }

	public String GetTitle() {
		return this.SeleniumDriver.getTitle();
	}
	
	public void OpenPageByURL() {
		this.WebsiteAutomator.OpenPageByURL(this.GetUrl());
	}
	
	public void Button_ByName_Click(String buttonName) {
		this.SeleniumAssistant.Button_ByName_Click(buttonName);
	}
	
	public String GetFieldIDFromLabel(String Label) throws Exception {
		return GetFieldFromLabel(Label).ID;
	}
	
	protected PageField GetFieldByID(String ID) throws Exception {
		List<PageField> matchedFields = this.PageFieldList.stream().filter(p -> p.ID !=null && p.ID.equals(ID)).collect(Collectors.toList());
		if(matchedFields.size() != 1)
			throw new Exception("Found " + matchedFields.size() + "fields for " + ID);
		return matchedFields.get(0);	
	}
	
	protected PageField GetFieldByCode(String code) {
		List<PageField> matchedFields = this.PageFieldList.stream().filter(p -> p.Code!=null && p.Code.equals(code)).collect(Collectors.toList());
		return matchedFields.get(0);	
	}

	protected PageField GetFieldFromLabel(String Label) throws Exception {
		List<PageField> matchedFields = this.PageFieldList.stream().filter(p -> p.Label !=null && p.Label.equals(Label)).collect(Collectors.toList());
		if(matchedFields.size() != 1)
			throw new Exception("Found " + matchedFields.size() + "fields for " + Label);
		return matchedFields.get(0);
	}

	protected List<String> GetAllFieldLabels() {
		return this.PageFieldList.stream().map(PageField::getLabel).collect(Collectors.toCollection(ArrayList::new));
	}

	public void EnterFieldValueByLabel(String key, String value) throws Exception {
		EnterFieldValueByID(GetFieldIDFromLabel(key), value);
	}

	public void EnterFieldValueByID(String fieldID, String fieldValueToEnter) throws Exception {
		if(fieldValueToEnter!=null) {
			PageField control=GetFieldByID(fieldID);
			if(control.ControlType == ControlType.textbox) {
				this.SeleniumAssistant.TextBox_SetText(fieldID, fieldValueToEnter);
			} else if(control.ControlType == ControlType.dropdown) {
				this.SeleniumAssistant.DropDown_SelectText(fieldID, fieldValueToEnter);
			}
		}
	}
}
