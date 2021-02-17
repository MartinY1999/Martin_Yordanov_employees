package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import helpers.Logger;
import helpers.Validator;
import models.Employee;
import models.Project;
import utils.Pair;

public class Engine {
	private final Set<Integer> setOfIds;
	private final Map<Integer, Employee> mapOfEmployees;
	
	private List<Pair<Employee, Employee>> pairsOfEmployeesWithCommonProjects;
	
	public Engine(Set<Integer> set, Map<Integer, Employee> map) {
		this.setOfIds = set;
		this.mapOfEmployees = map;
		this.pairsOfEmployeesWithCommonProjects = new ArrayList<>();
	}
	
	private void findAllPairsWithCommonProjects() {
		List<Integer> ids = new ArrayList<>(setOfIds);
		
		for (int i = 0; i < ids.size() - 1; i++) {
			Employee current = this.mapOfEmployees.get(ids.get(i));
			
			for (int j = i + 1; j < ids.size(); j++) {
				Employee compareEmp = this.mapOfEmployees.get(ids.get(j));
				
				if (haveCommonProjects(current, compareEmp)) {
					Pair<Employee, Employee> empPair = new Pair<>(current, compareEmp);
					
					this.pairsOfEmployeesWithCommonProjects.add(empPair);
				}
			}
		}
	}
	
	private boolean haveCommonProjects(Employee emp1, Employee emp2) {
		List<Integer> commonProjects = this.commonProjectsIds(emp1, emp2);
		
		if (commonProjects.size() == 0) {
			return false;
		}
		
		return true;
	}
	
	private List<Integer> commonProjectsIds(Employee emp1, Employee emp2) {
		List<Integer> emp1Projects = emp1.getProjectsIds();
		List<Integer> common = new ArrayList<>(emp2.getProjectsIds());
		
		common.retainAll(emp1Projects);
		
		return common;
	}
	
	public String pairWithLongestTimeOnProject() {
		long maxTime = Long.MIN_VALUE;
		int resultEmp1ID = -1;
		int resultEmp2ID = -1;
		int resultProjectID = -1;
		
		if (!Validator.validateEngineData(this.setOfIds, this.mapOfEmployees)) {
			return "";
		}
		
		this.findAllPairsWithCommonProjects();
		
		for (Pair<Employee, Employee> pair : this.pairsOfEmployeesWithCommonProjects) {
			Pair<Long, Integer> currentMaxTime = getProjectWithLongestTime(pair.getL(), pair.getR());
			
			if (maxTime < currentMaxTime.getL()) {
				maxTime = currentMaxTime.getL();
				
				resultEmp1ID = pair.getL().getEmpID();
				resultEmp2ID = pair.getR().getEmpID();
				resultProjectID = currentMaxTime.getR();
			}
		}
		
		if (!Validator.validateResultData(maxTime, resultEmp1ID, resultEmp2ID, resultProjectID)) {
			return "No result";
		}
		
		return produceResult(maxTime, resultEmp1ID, resultEmp2ID, resultProjectID);
	}
	
	private Pair<Long, Integer> getProjectWithLongestTime(Employee emp1, Employee emp2) {
		long max = Long.MIN_VALUE;
		int resultId = -1;
		
		List<Integer> commonProjects = this.commonProjectsIds(emp1, emp2);
			
		for (Integer id : commonProjects) {
			Project emp1Project = emp1.findProject(id);
			Project emp2Project = emp2.findProject(id);
			
			Date emp1StartDate = emp1Project.getStartDate();
			Date emp2StartDate = emp2Project.getStartDate();
			Date emp1EndDate = emp1Project.getEndDate();
			Date emp2EndDate = emp2Project.getEndDate();
			
			Date startDate = emp1StartDate.compareTo(emp2StartDate) < 0 ? emp2StartDate : emp1StartDate;
			Date endDate = emp1EndDate.compareTo(emp2EndDate) < 0 ? emp1EndDate : emp2EndDate;
			
			long result = endDate.getTime() - startDate.getTime();
			
			if (result > max) {
				max = result;
				resultId = id;
			}
		}
		
		return new Pair<Long, Integer>(max, resultId);
	}
	
	private String produceResult(long time, int emp1Id, int emp2Id, int projectId) {
		StringBuilder builder = new StringBuilder();
		
		long days = TimeUnit.MILLISECONDS.toDays(time);
		
		long years = days / 365;
		days %= 365;
		long months = days / 30;
		days %= 30;
		long weeks = days / 7;
		days %= 7;
		
		builder.append("Employees: " + emp1Id + ", " + emp2Id);
		builder.append(System.getProperty("line.separator"));
		builder.append("They have the longest time working on a common project:");
		builder.append(System.getProperty("line.separator"));
		builder.append(years + " years, " + months + " months, " + weeks + " weeks and " 
				+ days + " days");
		builder.append(System.getProperty("line.separator"));
		builder.append("Project ID: " + projectId);
		
		Logger.log("Successfully produced result!");
		
		return builder.toString();
	}
}
