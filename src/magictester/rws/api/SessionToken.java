package magictester.rws.api;

import java.util.Date;

import magictester.core.IniFile;

public class SessionToken {
	String token=null;
	Date acquired=null;
	long expires=-1;
	
	String testSessionName;
	String testProfileName;
	
	public SessionToken(String testSessionName, String testProfileName,
			String token, long expiresSeconds) throws Exception {
		this(testSessionName, testProfileName);
		setToken(token, expiresSeconds);
	}

	public SessionToken(String testSessionName, String testProfileName) {
		this.testSessionName=testSessionName;
		this.testProfileName=testProfileName;
	}

	void setToken(String varToken, long expiresSeconds) throws Exception {
		token=varToken;
		expires=expiresSeconds;
		acquired=new Date();
		IniFile ini = new IniFile("TestData.ini");
		String section="APIToken/" + testSessionName + "/" + testProfileName;
		ini.setValue(section, "token", varToken);
		ini.setValue(section, "expires", expires);
		ini.setValue(section, "acquired", acquired);
		ini.saveToFile();
	}
	
	public String getToken() {
		if(IsValid())
			return token;
		return null; 
	}
	
	public Boolean IsValid() {
		if(token==null) LoadFromFile();
		if(token==null) return false;
		if(acquired.getTime() + 1000*expires < (new Date().getTime())) return false;
		return true;
	}

	public void LoadFromFile() {
		IniFile ini = new IniFile("TestData.ini");
		String section="APIToken/" + testSessionName + "/" + testProfileName;
		this.token=ini.getValue(section, "token");
		if(this.token==null) return;
		System.out.println("loaded token from the ini file...");
		this.expires=ini.getValue(section, "expires", (long)-1);
		this.acquired=ini.getValue(section, "acquired", new Date(0));
	}
}

