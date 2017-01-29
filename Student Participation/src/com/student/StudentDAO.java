package com.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBconnection.DBconnect;

public class StudentDAO {
	
	static DBconnect dao = new DBconnect();
	static Connection con = null;			//create connection
	
	
	/*-----------Check student---------*/
	
	
	public boolean checkStudent(String studId, String subjCode){
		
		con = dao.getConnection();	//get connection
		
		String sql = "select stud_id from valid_student where stud_id = ? and subj_code = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, studId);
			stmt.setString(2, subjCode);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{									//close all conection and statement
			try {
				stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			dao.closeConnection(con);
		}
		
		return false;
	}
	
	
	/*----------Register Student-------------*/
	
	public void regStudent(Student bean){
		
		Connection con = dao.getConnection();	//get connection
		
		String sql = "insert into student (stud_id, stud_name, stud_email, stud_pass, stud_phone) values (?, ?, ?, ?, ?)";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql);				
			stmt.setString(1, bean.getStudId());				//prepare data to be execute
			stmt.setString(2, bean.getStudName());
			stmt.setString(3, bean.getStudEmail());
			stmt.setString(4, bean.getStudPass());
			stmt.setString(5, bean.getStudPhone());
			stmt.execute();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{									//close all conection and statement
			try {
				stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			dao.closeConnection(con);
		}
		
		
	}
	
	public boolean loginStud(String id, String pass){
		
		boolean exist = false;
		
		con = dao.getConnection();
		
		String sql = "select * from student where stud_id =? and stud_pass = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, pass);
			rs = stmt.executeQuery();
			
			exist = rs.next();	
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			dao.closeConnection(con);
		}
		
		return exist;
		
	}
	
	public List<Student> studentList(String lecId, String subjName, String groupId){
		
		List<Student> studentArray = new ArrayList<Student>();
		
		//System.out.println(lecId);
		//System.out.println(subjName);
		//System.out.println(groupId);
		
		con = dao.getConnection();
		
		String sql = "select * from Student S join Enroll E on S.stud_id = E.stud_id join Class C on E.class_id = C.class_id join Subject Sj on C.sub_code = Sj.sub_code where C.lec_id=? and Sj.sub_name=? and C.groupc_id = ?";

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			
			statement = con.prepareStatement(sql);
			
			statement.setString(1, lecId);
			statement.setString(2, subjName);
			statement.setString(3, groupId);
			
			resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				
				Student student = new Student();
				
				student.setStudId(resultSet.getString("stud_id"));
				student.setStudName(resultSet.getString("stud_name"));
				student.setStudEmail(resultSet.getString("stud_email"));
				student.setStudPhone(resultSet.getString("stud_phone"));
				//System.out.println(resultSet.getString("stud_id"));
				//System.out.println("object = "+student);
				
				studentArray.add(student);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dao.closeConnection(con);
		}
		
		return studentArray;
	}
	
	public List<Student> classMateDetails(String subjName, String groupId){
		
		List<Student> studentArray = new ArrayList<Student>();
		
		con = dao.getConnection();
		
		String sql = "select * from Student S join Enroll E on S.stud_id = E.stud_id join Class C on E.class_id = C.class_id join Subject Sj on C.sub_code = Sj.sub_code where Sj.sub_name=? and C.groupc_id = ?";

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			
			statement = con.prepareStatement(sql);

			statement.setString(1, subjName);
			statement.setString(2, groupId);
			
			resultSet = statement.executeQuery();
			
			boolean exist = resultSet.next();
			
			while(exist){
				
				Student student = new Student();
				
				student.setStudId(resultSet.getString("stud_id"));
				student.setStudName(resultSet.getString("stud_name"));
				student.setStudEmail(resultSet.getString("stud_email"));
				
				System.out.println("object = "+student);
				
				studentArray.add(student);
				exist = resultSet.next();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dao.closeConnection(con);
		}
		
		return studentArray;
	}
	
	
	public int getStudentClass(String studId, String subjCode, String groupId){
		
		
		int classId = 0;
		
		con = dao.getConnection();
		
		String sql = "select C.class_id as c_id from Class C join Enroll E on C.class_id = E.class_id where E.stud_id = ? and C.sub_code = ? and C.groupc_id = ?";
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			System.out.println("hello");
			statement = con.prepareStatement(sql);
			
			statement.setString(1, studId);
			statement.setString(2, subjCode);
			statement.setString(3, groupId);
			
			resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				
				classId = resultSet.getInt("c_id");
				System.out.println(classId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dao.closeConnection(con);
		}
		
		return classId;
	}
	

}
