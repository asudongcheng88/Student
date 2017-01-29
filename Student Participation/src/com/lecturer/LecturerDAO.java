package com.lecturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBconnection.DBconnect;



public class LecturerDAO {
	
	static DBconnect dao = new DBconnect();
	static Connection con = null;
	
	public void regLecturer(String id, String name, String email, String pass){
		
		con = dao.getConnection();
		String sql = "insert into Lecturer (lec_id, lec_name, lec_email, lec_pass) values (?, ?, ?, ?)";
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, name);
			stmt.setString(3, email);
			stmt.setString(4, pass);
			
			stmt.execute();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			dao.closeConnection(con);
		}
	}
	
	public boolean loginLect(String id, String pass){
		
		boolean exist = false;
	
		con = dao.getConnection();
		
		String sql = "select * from lecturer where lec_id = ? and lec_pass = ?";
		
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
}
