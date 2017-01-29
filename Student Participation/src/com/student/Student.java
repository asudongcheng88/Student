package com.student;

public class Student {
	
	private String studId;
	private String studName;
	private String studEmail;
	private String studPass;
	private String studPhone;
	public String getStudId() {
		return studId;
	}
	public void setStudId(String studId) {
		this.studId = studId;
	}
	public String getStudName() {
		return studName;
	}
	public void setStudName(String studName) {
		this.studName = studName;
	}
	public String getStudEmail() {
		return studEmail;
	}
	public void setStudEmail(String studEmail) {
		this.studEmail = studEmail;
	}
	public String getStudPass() {
		return studPass;
	}
	public void setStudPass(String studPass) {
		this.studPass = studPass;
	}
	public String getStudPhone() {
		return studPhone;
	}
	public void setStudPhone(String studPhone) {
		this.studPhone = studPhone;
	}
	public Student() {
		super();
	}
	public Student(String studId, String studName) {
		super();
		this.studId = studId;
		this.studName = studName;
	}
	
	
}
