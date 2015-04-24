package magictester.core;

import magictester.core.SuperWebPageAutomator.ControlType;

import org.openqa.selenium.By;

public class PageField {
	public String Code;
	public By By;
	public String Label;
	public String ID;
	public String Name;
	public ControlType ControlType;
	public String getLabel() {
		return this.Label;
	}
    public PageField(String fieldLabel, String ID, ControlType controlType){
            this.Label=fieldLabel;
            this.ID=ID;
            this.ControlType=controlType;
    }

    public PageField(String code, By by){
    	this.By=by;
    	this.Code=code;
    }
    			
}
