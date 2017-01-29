package com.classes;

public class Classes {
	
	private int classId;
	private String groupId;
	private String subjCode;
	private String subjName;
	private int total;
	private String lecId;
	private String lecName;
	private int reqLeave;
	
	

	public int getReqLeave() {
		return reqLeave;
	}
	public void setReqLeave(int reqLeave) {
		this.reqLeave = reqLeave;
	}
	public String getLecName() {
		return lecName;
	}
	public void setLecName(String lecName) {
		this.lecName = lecName;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getSubjName() {
		return subjName;
	}
	public void setSubjName(String subjName) {
		this.subjName = subjName;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getSubjCode() {
		return subjCode;
	}
	public void setSubjCode(String subjCode) {
		this.subjCode = subjCode;
	}
	public String getLecId() {
		return lecId;
	}
	public void setLecId(String lecId) {
		this.lecId = lecId;
	}
	public Classes() {
		super();
	}
	public Classes(String groupId, String subjCode, String subjName, int total) {
		super();
		this.groupId = groupId;
		this.subjCode = subjCode;
		this.subjName = subjName;
		this.total = total;
		
	}
	
	
	
}
