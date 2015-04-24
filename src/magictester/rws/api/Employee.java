package magictester.rws.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Employee {
	public String FirstName;
	public String ID;
	public String AccountID;
	public String LastName;
	public String Gender;
	public String Timezone;
	public String ClockOnNonWorkDays;
	public String Employment_Date;
	public String DateOfBirth;
	public String PrimaryWorksiteID;

	public static List<Employee> ListFactory(APIV3Response resp) throws Exception {
		List<Employee> ret=new ArrayList<Employee>();
		for(LinkedHashMap<String, Object> itemJSON : resp.GetJSONNodesListString("$.employees")) {
			Employee emp=new Employee();
			emp.Populate(itemJSON);
			ret.add(emp);
		}
		return ret;
	}

	String ConvertToString(Object val) {
		String ret="";
		try {
			if(val==null) {
			} else if(val.getClass() == Integer.class) {
				int i=(int)val;
				ret=Integer.toString(i);
			} else if(val.getClass() == Boolean.class) {
				Boolean i=(Boolean)val;
				if(i) ret="true";
				else  ret="false";
			} else {
				ret=(String) val;
			}
		} catch(Exception ex) {
			System.out.println(val.getClass().toString());
		}
		return ret;
	}
	

	void Populate(LinkedHashMap<String, Object> itemJSOM) {
		//this.ID=Integer.toString(
		this.ID=		ConvertToString(itemJSOM.get("id"));				
		this.AccountID=ConvertToString(itemJSOM.get("account_id"));				
		this.LastName=ConvertToString(itemJSOM.get("lastname"));				
		this.FirstName=ConvertToString(itemJSOM.get("firstname"));				
		this.Gender=ConvertToString(itemJSOM.get("gender"));				
		this.Timezone=ConvertToString(itemJSOM.get("timezone"));				
		this.ClockOnNonWorkDays=ConvertToString(itemJSOM.get("clock_on_non_work_days"));				
		this.Employment_Date=ConvertToString(itemJSOM.get("employment_date"));				
		this.DateOfBirth=ConvertToString(itemJSOM.get("date_of_birth"));				
		this.PrimaryWorksiteID=ConvertToString(itemJSOM.get("primary_worksite_id"));				
		//this.XXXXX=itemJSOM.getOrDefault("avatar");				
		//this.XXXXX=itemJSOM.getOrDefault("profile");				
	}
}

