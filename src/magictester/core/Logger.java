package magictester.core;

import java.sql.Timestamp;
import java.util.Date;

public class Logger {

	public static void Info(String message) {
		LogWithPrefix(message);
	}

	public static void Error(String message) {
		LogWithPrefix("ERROR: " + message);
	}

	private static void LogWithPrefix(String message) {
		String logLine="";
		logLine+=new Timestamp(new Date().getTime());
		logLine+=" --- ";
		logLine+=message;
		System.out.println(logLine);
	}

	public static void Info(int i) {
		Info(Integer.toString(i));
	}
}
