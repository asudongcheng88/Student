package com.attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group.Group;
import com.group.GroupDAO;
import com.student.Student;
import com.student.StudentDAO;
import com.subject.Subject;

import DBconnection.DBconnect;

public class AttendanceDAO {
	
	DBconnect dao = new DBconnect();
	Connection con = null;
	
	public int getClassId(String lecId, String subjcode, String groupId){
		
		int classId = 0;
		con = dao.getConnection();
		
		String sql = "select class_id from Class where lec_id = ? and sub_code = ? and groupc_id = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, lecId);
			stmt.setString(2, subjcode);
			stmt.setString(3, groupId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				classId = rs.getInt("class_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dao.closeConnection(con);
		}
		
		return classId;
	}
	
	public void insertAttendance(int classId, String studId[], String todayDate){
		
		con = dao.getConnection();
		
		String sql = "insert into Attendance (stud_id, class_id, attd_date, is_present) values (?, ?, to_date(?, 'dd/MM/yyyy'), ?)";
		
		PreparedStatement stmt = null;
		
		try {
			con.setAutoCommit(false);
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		try {
			stmt = con.prepareStatement(sql);
			
			for(int i = 0; i < studId.length; i++){
				
				System.out.println(studId[i]);
				System.out.println(todayDate);
				
				stmt.setString(1, studId[i]);
				stmt.setInt(2, classId);
				stmt.setString(3, todayDate);
				stmt.setInt(4, 1);
				
				stmt.addBatch();
				
			}
			
			stmt.executeBatch();
			con.commit();
	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				stmt.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dao.closeConnection(con);
		}
	}
	
	/*
	 *	1) get the student list to get name and id
	 *	2) get the class id
	 *	3) count the total classes already held
	 *	4) for every student, count present
	 *	5) insert all the information into the array 
	 * 
	 */
	
	public List<Attendance> studentAttendance(String lecId, String subjName, String subjCode, String groupId){
		
		int present = 0;
		
		
		
		String sql = "select count(attd_date) AS present_days from Attendance where stud_id = ? and class_id = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Attendance> attdArray = new ArrayList<Attendance>();
		List<Student> studentArray = new ArrayList<Student>();
		
		StudentDAO studentDAO = new StudentDAO();
		
		studentArray = studentDAO.studentList(lecId, subjName, groupId);	
		
		int classId = getClassId(lecId, subjCode, groupId);
		
		int totalDayClass = countDistinctDate(subjCode, groupId);
		
		//System.out.println("lec id = " +lecId);
		//System.out.println("subj code = " +subjCode);
		//System.out.println("group id = " +groupId);
		
		//System.out.println("total day class " +totalDayClass);
		
		for(Student std: studentArray){
			
			con = dao.getConnection();
			
			String studId = std.getStudId();
			String studName = std.getStudName();
			
			//System.out.println("student id = " +studId);
			//System.out.println("student name = " +studName);
			
			
			try {
				stmt = con.prepareStatement(sql);
				
				stmt.setString(1, studId);
				stmt.setInt(2, classId);
				
				rs = stmt.executeQuery();
				
				while(rs.next()){
					
					present = rs.getInt("present_days");
					//System.out.println("total present " +present);
				}
				
				Attendance attd = new Attendance();
				
				attd.setStudId(studId);
				attd.setStudName(studName);
				attd.setPresent(present);
				attd.setTotalDayClass(totalDayClass);
				
				attdArray.add(attd);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					stmt.close();
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dao.closeConnection(con);
			}
			
		}
		
		
		return attdArray;
	}
	
	
	//count days for classes already held
	
	public int countDistinctDate(String subjCode, String groupId){
		
		int countDate = 0;
		
		con = dao.getConnection();
		
		String sql = "select count(DISTINCT A.attd_date) AS total_class_held from Attendance A join Class C on A.class_id = C.class_id where C.sub_code = ? and C.groupc_id = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, subjCode);
			stmt.setString(2, groupId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				countDate = rs.getInt("total_class_held");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dao.closeConnection(con);
		}
		
		
		return countDate;
	}
	
	public List<Group> getSubjectAndGroup(List<Subject> teachSubject, String lecId){
		
		List<Group> subjGroupArray = new ArrayList<Group>();
		List<Group> groupArray = new ArrayList<Group>();
		GroupDAO groupDAO = new GroupDAO();
		
		for(Subject tSubj: teachSubject){
			
			String subjName = tSubj.getSubjName();
			String subjCode = tSubj.getSubjCode();
			groupArray = groupDAO.lecSubjectGroupList(lecId, subjName);
			
			for(Group g:groupArray){
				
				Group group = new Group();
				
				group.setGroupId(g.getGroupId());
				group.setSubjName(subjName);
				group.setSubjCode(subjCode);
				
				subjGroupArray.add(group);
			}
			
		}
		return subjGroupArray;
		
	}
	
	public List<Attendance> attendanceAlert(List<Group> subjGroupArray, String lecId){
		
		
		List<Attendance> attdArray = new ArrayList<Attendance>();
		List<Attendance> attdAlertArray = new ArrayList<Attendance>();
		
		for(Group g:subjGroupArray){
			
			String subjName = g.getSubjName();
			String subjCode = g.getSubjCode();
			String groupId = g.getGroupId();
			
			attdArray = studentAttendance(lecId, subjName, subjCode, groupId);
			
			for(Attendance att:attdArray){
				
				int absent = att.getTotalDayClass() - att.getPresent();
				
				if(absent >= 3){
					
					Attendance attd = new Attendance();
					
					attd.setStudName(att.getStudName());
					attd.setSubjCode(subjCode);
					attd.setGroupId(groupId);
					attd.setAbsence(absent);
					
					attdAlertArray.add(attd);
					
				}
			}
			
			
		}
		return attdAlertArray;
		
	}
	
	public int attendanceRecord (String studId, String subjCode, String groupId){
		
		int present = 0;
		
		String sql = "select count(distinct A.attd_date) as present from attendance A join Class C on A.class_id = C.class_id where A.stud_id = ? and C.sub_code = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
			
		con = dao.getConnection();
			
			
		try {
			stmt = con.prepareStatement(sql);
				
			stmt.setString(1, studId);
			stmt.setString(2, subjCode);
				
			rs = stmt.executeQuery();
				
			while(rs.next()){
					
				present = rs.getInt("present");
					//System.out.println("total present " +present);
			}
				
				
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dao.closeConnection(con);
		}
			
		
		
		
		return present;
		
		
	}


}
