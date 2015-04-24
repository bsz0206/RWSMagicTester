package magictester.core;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class UtilityFunctions {

	public static boolean isNumeric(String str) {
		@SuppressWarnings("unused")
		double d=-1;
		try {
			d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static String Join(List<String> valueList, String delimiter) {
		String ret="";

		for (String s : valueList) {
			if(ret.length()>0) ret+=delimiter;
		    ret += s;
		}		
		
		return ret;
	}
	
	public static Document loadXMLFromString(String xml)
	{
	    Document ret=null;	
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		    InputSource is = new InputSource(new StringReader(xml));
			ret = builder.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return ret; 
	}

	public static Node GetSubNodeByXPath(Node rootNode, String XPath) {
		Node ret=null;
		XPath xPathObj =  XPathFactory.newInstance().newXPath();
		try {
			ret = (Node) xPathObj.compile(XPath).evaluate(rootNode, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static NodeList GetSubNodeSetByXPath(Node rootNode, String XPath) {
		NodeList ret=null;
		XPath xPathObj =  XPathFactory.newInstance().newXPath();
		try {
			ret = (NodeList) xPathObj.compile(XPath).evaluate(rootNode, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void Sleep(long ms) {
		try { Thread.sleep(ms); } catch (InterruptedException e) {}
	}

	public static String getXPathForOneOfClassIS(String className) {
		return "//*[contains(concat(\" \", normalize-space(@class), \" \"), \" " + className + " \")]";
	}

	public static Date ConvertEpochTimeToUTC(long epochTime) {
		long zoneTimeDiffMilis=TimeZone.getDefault().getRawOffset();
		Date ret = new Date(1000*epochTime - zoneTimeDiffMilis);
		return ret;
	}

	public static String URLEncode(String source) throws UnsupportedEncodingException {
		return URLEncoder.encode(source, "UTF-8").replace("+", "%20");
	}

	public static Date getSystemTimeUTC() {
		return UtilityFunctions.ConvertEpochTimeToUTC(System.currentTimeMillis()/1000);
	}

}
