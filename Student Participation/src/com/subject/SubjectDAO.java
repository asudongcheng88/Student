package com.subject;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.classes.Classes;

import DBconnection.DBconnect;

public class SubjectDAO {
	
	static DBconnect dao = new DBconnect();
	static Connection con = null;
	
	public void registerSubject(String subjCode, String subjName){
		
		con = dao.getConnection();
		
		String sql = "insert into Subject (sub_code, sub_name) values (?,?)";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, subjCode);
			stmt.setString(2, subjName);
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
	
	public List<Subject> listTeachSubject(String lecId){
		
		List<Subject> subjectArray = new ArrayList<Subject>();
		
		con = dao.getConnection();
		
		String sql = "select S.sub_code, S.sub_name from Subject S join Class C on S.sub_code = C.sub_code where lec_id=?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, lecId);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				System.out.println("lol "+rs.getString("sub_name"));
				Subject subject = new Subject();
				subject.setSubjName(rs.getString("sub_name"));
				subject.setSubjCode(rs.getString("sub_code"));
				
				subjectArray.add(subject);
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
		
		return subjectArray;   
		
	}
	
	public List<Subject> studentSubjectList(String studId){
		
		List<Subject> subjectArray = new ArrayList<Subject>();
		
		con = dao.getConnection();
		
		String sql = "select S.sub_name, S.sub_code from Subject S join Class C on S.sub_code = C.sub_code join Enroll E on E.class_id = C.class_id where E.stud_id=?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, studId);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				String subjName = rs.getString("sub_name");
				String subjCode = rs.getString("sub_code");
				
				Subject subject = new Subject();
				
				subject.setSubjCode(subjCode);
				subject.setSubjName(subjName);
				
				subjectArray.add(subject);
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
		
		return subjectArray;   
		
	}
	
	

}
