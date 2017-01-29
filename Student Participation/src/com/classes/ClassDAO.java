package com.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBconnection.DBconnect;

public class ClassDAO {

	static DBconnect dao = new DBconnect();
	static Connection con = null;
	
	public void registerClass(String groupId, String subjCode, String lecId){
		
		con = dao.getConnection();
		
		String sql = "insert into Class (groupc_id, sub_code, lec_id) values (?, ?, ?)";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, groupId);
			stmt.setString(2, subjCode);
			stmt.setString(3, lecId);
			stmt.execute();
			
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
	
	public List<Classes> classDetails(String lecId){
		
		List<Classes> classArray = new ArrayList<Classes>();
		
		con = dao.getConnection();
		
		String sql = "select C.groupc_id, C.sub_code, S.sub_name from Class C join Subject S on C.sub_code = S.sub_code where C.lec_id=?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, lecId);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				String groupId = rs.getString("groupc_id");
				String subjCode = rs.getString("sub_code");
				String subjName = rs.getString("sub_name");
				
				
				int totalStud = countStudent(lecId, subjCode, groupId);
				
				//System.out.println("Data will be shown");
				//System.out.println(groupId);
				//System.out.println(subjCode);
				//System.out.println(subjName);
				
				Classes classes = new Classes(groupId, subjCode, subjName, totalStud); 
				
				classArray.add(classes);
				
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
		
		return classArray;
		
	}
	
	public List<Classes> distinctSubjectList(){
		//System.out.println("Enter method");
		List<Classes> subjectArray = new ArrayList<Classes>();
		
		con = dao.getConnection();
		
		String sql = "select DISTINCT S.sub_name, C.sub_code from Class C join Subject S on C.sub_code = S.sub_code ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				Classes classes = new Classes();
				//classes.setClassId(rs.getInt("class_id"));
				//classes.setGroupId(rs.getString("groupc_id"));
				//System.out.println(rs.getString("sub_code"));
				//System.out.println(rs.getString("sub_name"));
				classes.setSubjCode(rs.getString("sub_code"));
				classes.setSubjName(rs.getString("sub_name"));
				subjectArray.add(classes);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dao.closeConnection(con);
		}
		
		return subjectArray;
		
	}
	
	public List<Classes> distinctGroupList(String subjCode){
		
		List<Classes> groupArray = new ArrayList<Classes>();
		
		con = dao.getConnection();
		
		String sql = "select DISTINCT groupc_id from Class where sub_code = ? ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, subjCode);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				Classes classes = new Classes();
				//classes.setClassId(rs.getInt("class_id"));
				classes.setGroupId(rs.getString("groupc_id"));
				//classes.setSubjCode(rs.getString("sub_code"));
				//classes.setSubjName(rs.getString("sub_name"));
				//System.out.println(rs.getString("groupc_id"));
				groupArray.add(classes);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dao.closeConnection(con);
		}
		
		return groupArray;
		
	}
	
	public List<Classes> studentViewClass(String studId){
		
		List<Classes> classArray = new ArrayList<Classes>();
		
		con = dao.getConnection();
		
		String sql = "select C.class_id, C.groupc_id, S.sub_name, S.sub_code, L.lec_name, E.req_leave from Enroll E join Class C on E.class_id = C.class_id join Subject S on C.sub_code = S.sub_code join Lecturer L on C.lec_id = L.lec_id where E.stud_id = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, studId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Classes classes = new Classes();
				//System.out.println("class_id = "+rs.getString("class_id"));
				classes.setClassId(rs.getInt("class_id"));
				classes.setGroupId(rs.getString("groupc_id"));
				classes.setSubjName(rs.getString("sub_name"));
				classes.setSubjCode(rs.getString("sub_code"));
				classes.setLecName(rs.getString("lec_name"));
				classes.setReqLeave(rs.getInt("req_leave"));
				
				classArray.add(classes);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return classArray;
	}
	
	public int countStudent(String lecId, String subjCode, String groupId){
		
		int totalStudent = 0;
		
		con = dao.getConnection();
		
		String sql = "select count(E.stud_id) as total from Enroll E join Class C on E.class_id = C.class_id where C.lec_id=? and C.sub_code=? and C.groupc_id=? ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, lecId);
			stmt.setString(2, subjCode);
			stmt.setString(3, groupId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				totalStudent = rs.getInt("total");
			}
		} catch (SQLException e) {
			
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
		
		
		return totalStudent;
		
	}
}
