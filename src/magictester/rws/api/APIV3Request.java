package magictester.rws.api;

import java.util.ArrayList;
import java.util.List;

public class APIV3Request {
	List<APIV3RequestItem> items;
	
	public APIV3Request() {
		items = new ArrayList<APIV3RequestItem>();
	}
	
	public void AddArgument(String argName, String argValue){
		items.add(new APIV3RequestItem(argName, argValue));
	}
	
	public class APIV3RequestItem {
		String argName;
		String argValue;
		
		public APIV3RequestItem(String argName, String argValue) {
			this.argName=argName;
			this.argValue=argValue;
		}
	}

	public String GetRETSUrl(String baseURL, String methodName, APIV3Request requ) {
		String ret=baseURL;
		ret+="/";
		ret+=methodName;
		ret+="/";
		for(APIV3RequestItem reqitem : requ.items)
		{
			ret+=reqitem.argName;
			ret+="=";
			ret+=reqitem.argValue;
		}
		return ret;
	}
}
