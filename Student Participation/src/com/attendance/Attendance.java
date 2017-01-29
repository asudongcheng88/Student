package com.attendance;

public class Attendance {

	private String studName;
	private String studId;
	private String subjName;
	private String subjCode;
	private String groupId;
	private int absence;
	private int present;
	private int totalDayClass;
	
	
	public int getAbsence() {
		return absence;
	}
	public void setAbsence(int absence) {
		this.absence = absence;
	}
	public String getSubjName() {
		return subjName;
	}
	public void setSubjName(String subjName) {
		this.subjName = subjName;
	}
	public String getSubjCode() {
		return subjCode;
	}
	public void setSubjCode(String subjCode) {
		this.subjCode = subjCode;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public int getTotalDayClass() {
		return totalDayClass;
	}
	public void setTotalDayClass(int totalDayClass) {
		this.totalDayClass = totalDayClass;
	}
	public String getStudName() {
		return studName;
	}
	public void setStudName(String studName) {
		this.studName = studName;
	}
	public String getStudId() {
		return studId;
	}
	public void setStudId(String studId) {
		this.studId = studId;
	}
	public int getPresent() {
		return present;
	}
	public void setPresent(int present) {
		this.present = present;
	}
	public Attendance() {
		super();
	}
	
	
}
