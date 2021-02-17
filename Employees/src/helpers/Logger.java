package helpers;

public class Logger {
	private static StringBuilder builder = new StringBuilder();
	
	private Logger() {
		
	}
	
	public static void log(String message) {
		Logger.builder.append(message);
		Logger.builder.append(System.getProperty("line.separator"));
	}
	
	public static String logResult() {
		return Logger.builder.toString();
	}
}
