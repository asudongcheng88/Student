package com.subject;

public class Subject {

	private String subjCode;
	private String subjName;
	
	public String getSubjCode() {
		return subjCode;
	}
	public void setSubjCode(String subjCode) {
		this.subjCode = subjCode;
	}
	public String getSubjName() {
		return subjName;
	}
	public void setSubjName(String subjName) {
		this.subjName = subjName;
	}
	public Subject() {
		super();
	}
	public Subject(String subjName) {
		super();
		this.subjName = subjName;
	}
	
	
}
