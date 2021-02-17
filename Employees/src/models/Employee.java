package models;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	private final int empID;
	private List<Project> employeeProjects;
	
	public Employee(int id) {
		this.empID = id;
		this.employeeProjects = new ArrayList<>();
	}

	public List<Project> getEmployeeProjects() {
		return employeeProjects;
	}
	
	public int getEmpID() {
		return empID;
	}
	
	private boolean containsProject(int projectId) {
		boolean isContained = false;
		
		for (Project current : employeeProjects) {
			if (current.getProjectID() == projectId) {
				isContained = true;
				break;
			}
		}
		
		return isContained;
	}
	
	public void addProject(Project project) {
		if (!containsProject(project.getProjectID())) {
			this.employeeProjects.add(project);
		}
	}
	
	public Project findProject(int id) {
		return this.employeeProjects
				.stream()
				.filter(x -> x.getProjectID() == id)
				.findFirst().get();
	}
	
	public List<Integer> getProjectsIds() {
		List<Integer> projectIds = new ArrayList<>();
		
		for (Project project : this.employeeProjects) {
			projectIds.add(project.getProjectID());
		}
		
		return projectIds;
	}
}
