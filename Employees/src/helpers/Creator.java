package helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.Employee;
import models.Project;

public class Creator {
	private final static String DATE_FORMAT = "yyyy-MM-dd";
	
	private Set<Integer> setOfIds;
	private Map<Integer, Employee> mapOfEmployees;
	
	public Creator() {
		this.setOfIds = new HashSet<>();
		this.mapOfEmployees = new HashMap<>();
	}

	public Set<Integer> getSetOfIds() {
		return setOfIds;
	}

	public Map<Integer, Employee> getMapOfEmployees() {
		return mapOfEmployees;
	}
	
	private Date createDate(String date) throws ParseException {
		if (date.compareTo("NULL") == 0) {
			Date dateTimeNow = new Date(System.currentTimeMillis());
			
			return dateTimeNow;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

		return formatter.parse(date);
	}
	
	public void create(String line) throws ParseException {
		String[] parts = line.split(", ");
		
		if (!Validator.validateLine(parts)) {
			Logger.log("Invalid count of elements in line!");
			return;
		}
		
		if (!Validator.validateEmployeeId(parts)) {
			Logger.log("Invalid component given for Employee ID");
			return;
		}
		
		if (!Validator.validateProjectId(parts)) {
			Logger.log("Invalid component given for Project ID");
			return;
		}
		
		if (!Validator.validateStartDate(parts)) {
			Logger.log("Invalid component given for Start Date");
			return;
		}
		
		if (!Validator.validateEndDate(parts)) {
			Logger.log("Invalid component given for End Date");
			return;
		}
		
		int empId = Integer.parseInt(parts[0]);
		int projectId = Integer.parseInt(parts[1]);
		Date startDate = createDate(parts[2]);
		Date endDate = createDate(parts[3]);
		
		Project currentProject = new Project(projectId, startDate, endDate);
		
		Logger.log("Project with Id: " + projectId 
				+ " successfully created!");
		
		this.setOfIds.add(empId);
		
		if (this.mapOfEmployees.get(empId) == null) {
			Employee currentEmployee = new Employee(empId);
			
			this.mapOfEmployees.put(empId, currentEmployee);
			
			Logger.log("Employee with Id: " + empId + " successfully created!");
		}
		
		Employee current = this.mapOfEmployees.get(empId);
		
		current.addProject(currentProject);
		
		this.mapOfEmployees.put(empId, current);
	}
}
