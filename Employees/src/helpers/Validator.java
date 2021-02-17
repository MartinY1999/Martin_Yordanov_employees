package helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import models.Employee;

public class Validator {
	private final static String DATE_FORMAT = "yyyy-MM-dd";
	
	private Validator() {
		
	}
	
	public static boolean validateLine(String[] parts) {
		if (parts.length != 4) {
			return false;
		}
		
		return true;
	}
	
	public static boolean validateEmployeeId(String[] parts) {
		try {
			int empId = Integer.parseInt(parts[0]);
			
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public static boolean validateProjectId(String[] parts) {
		try {
			int projectId = Integer.parseInt(parts[1]);
			
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public static boolean validateStartDate(String[] parts) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

			String dateInString = parts[2];
			Date date = formatter.parse(dateInString);
			
			return true;
		}
		catch (ParseException pe) {
			return false;
		}
	}
	
	public static boolean validateEndDate(String[] parts) {
		try {
			String dateInString = parts[2];
			
			if (dateInString.compareTo("NULL") == 0) {
				return true;
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

			Date date = formatter.parse(dateInString);
			
			return true;
		}
		catch (ParseException pe) {
			return false;
		}
	}
	
	public static boolean validateEngineData(Set<Integer> set, Map<Integer, Employee> map) {
		if (set.size() == 0 || map.size() == 0) {
			return false;
		}
		
		return true;
	}
	
	public static boolean validateResultData(long time, int empId1, int empId2, int prId) {
		if (time == Long.MIN_VALUE || empId1 == -1 || empId2 == -1 || prId == -1) {
			return false;
		}
		
		return true;
	}
}