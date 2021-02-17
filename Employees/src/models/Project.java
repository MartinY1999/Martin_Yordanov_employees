package models;

import java.util.Date;

public class Project {
	private final int projectID;
	private final Date startDate;
	private final Date endDate;
	
	public Project(int projectID, Date startDate, Date endDate) {
		this.projectID = projectID;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public int getProjectID() {
		return projectID;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
}
