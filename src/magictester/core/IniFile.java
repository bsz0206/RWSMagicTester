package magictester.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IniFile {
	String path=null;
	private Pattern _section = Pattern.compile("\\s*\\[([^]]*)\\]\\s*");
	private Pattern _keyValue = Pattern.compile("\\s*([^=]*)=(.*)");
	private Map<String, Map<String, String>> _entries = new HashMap<>();

	public IniFile(String path) {
		load(path);
	}

	public void load(String path) {
		this.path=path;
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line;
			String section = null;
			while ((line = br.readLine()) != null) {
				Matcher m = _section.matcher(line);
				if (m.matches()) {
					section = m.group(1).trim();
				} else if (section != null) {
					m = _keyValue.matcher(line);
					if (m.matches()) {
						setValue(section, m.group(1).trim(), m.group(2).trim());
					}
				}
			}
		} catch (Exception ex) {
			File f = new File(path);
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				if(br!=null)
					br.close();
			} catch (IOException e) {}
		}
	}

	@SuppressWarnings("deprecation")
	public Date getValue(String section, String key, Date date) {
		String strVal=getValue(section, key);
		if (strVal == null) return date;
		return new Date(Date.parse(strVal));
	}

	public String getValue(String section, String key) {
		Map<String, String> kv = _entries.get(section);
		if(kv==null) return null;
		return kv.get(key);
	}

	public String getValue(String section, String key, String defaultvalue) {
		String strVal=getValue(section, key);
		if (strVal == null) return defaultvalue;
		return strVal;
	}

	public int getValue(String section, String key, int defaultvalue) {
		String strVal=getValue(section, key);
		if (strVal == null) return defaultvalue;
		return Integer.parseInt(strVal);
	}

	public float getValue(String section, String key, float defaultvalue) {
		String strVal=getValue(section, key);
		if (strVal == null) return defaultvalue;
		return Float.parseFloat(strVal);
	}

	public long getValue(String section, String key, long defaultvalue) {
		String strVal=getValue(section, key);
		if (strVal == null) return defaultvalue;
		return Long.parseLong(strVal);
	}

	public double getValue(String section, String key, double defaultvalue) {
		String strVal=getValue(section, key);
		if (strVal == null) return defaultvalue;
		return Double.parseDouble(strVal);
	}

	public void setValue(String section, String valName, long value) {
		setValue(section, valName, Long.toString(value));
	}

	public void setValue(String section, String valName, Date value) {
		setValue(section, valName, value.toString());
	}

	public void setValue(String section, String valName, String value) {
		Map<String, String> kv = _entries.get(section);
		if (kv == null) {
			_entries.put(section, kv = new HashMap<>());
		}
		kv.put(valName, value);		
	}

	public String toString() {
		String ret="";
		for(Entry<String, Map<String, String>> section: getSections()) {
			ret+="\r\n";
			String sectionName=section.getKey();
			ret+="[" + sectionName + "]\r\n";
			for(Entry<String, String> value : getSectionVariables(section.getKey())) {
				ret+=value.getKey() + "=" + value.getValue() + "\r\n";
			}
		}
		return ret;
	}

	private Set<Entry<String, String>> getSectionVariables(String sectionName) {
		return getSectionByName(sectionName).entrySet();
	}

	private Set<Entry<String, Map<String, String>>> getSections() {
		return _entries.entrySet();
	}

	private Map<String, String> getSectionByName(String sectionName) {
		return _entries.get(sectionName);
	}

	public void saveToFile() throws Exception {
		if(path!=null)
			saveToFile(path);
		else
			throw new Exception("No file path to save");
	}

	public void saveToFile(String path) throws IOException {
		File f = new File(path);
		FileWriter writer = new FileWriter(f);
		writer.append(this.toString());
		writer.close();	
		if(this.path==path) this.path=path;
	}

}
