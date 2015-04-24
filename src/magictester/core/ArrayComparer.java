package magictester.core;

import java.util.ArrayList;
import java.util.List;

public class ArrayComparer {

	public static List<String> GetSinglesAtRight (List<String> left,	List<String> right) {
		List<String> ret=new ArrayList<String>();
		for (String item : right)
			if(!left.contains(item))
				ret.add(item);
		return ret;
	}

	public static List<String> GetSinglesAtLeft(List<String> left,	List<String> right) {
		return ArrayComparer.GetSinglesAtRight(right, left);
	}

	public static List<String> GetDuplicates(List<String> left,	List<String> right) {
		List<String> ret=new ArrayList<String>();
		for (String item : right)
			if(left.contains(item))
				ret.add(item);
		return ret;	
	}
}
