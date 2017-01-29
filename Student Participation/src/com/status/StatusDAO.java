package com.status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBconnection.DBconnect;

public class StatusDAO {
	
	DBconnect dao = new DBconnect();
	
	Connection con = null;
	
	//get the highest point in particular group and subject
	
	public int getHighestPoint(String lecId, String subjCode, String groupId){	
		
		int points = 0;
		
		con = dao.getConnection();
		
		String sql = "select MAX(E.points) from Enroll E join Class C on E.class_id = C.class_id where C.lec_id = ? and C.sub_code = ? and C.groupc_id = ?";
		//String sql = select
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, lecId);
			stmt.setString(2, subjCode);
			stmt.setString(3, groupId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				points = rs.getInt(1);
				
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
		
		return points;
	}

	public List<Status> studentListWithHighestPoint(String lecId, String subjCode, String groupId, int highestPoint){
		
		List<Status> statusArray = new ArrayList<Status>();
		
		con = dao.getConnection();
		
		String sql = "select S.stud_name, S.stud_id, E.points from Student S join Enroll E on E.stud_id = S.stud_id join Class C on E.class_id = C.class_id where C.lec_id = ? and C.sub_code = ? and C.groupc_id = ? and E.points = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, lecId);
			stmt.setString(2, subjCode);
			stmt.setString(3, groupId);
			stmt.setInt(4, highestPoint);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				Status status = new Status();
				status.setStudName(rs.getString("stud_name"));
				status.setStudId(rs.getString("stud_id"));
				status.setPoints(rs.getInt("points"));
				
				statusArray.add(status);
				
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
		
		
		return statusArray;
		
	}
	
	public int getSecondHighestPoint(String lecId, String subjCode, String groupId, int highestPoint){	
		
		int points = 0;
		
		con = dao.getConnection();
		
		String sql = "select MAX(E.points) from Enroll E join Class C on E.class_id = C.class_id where C.lec_id = ? and C.sub_code = ? and C.groupc_id = ? and E.points < ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, lecId);
			stmt.setString(2, subjCode);
			stmt.setString(3, groupId);
			stmt.setInt(4, highestPoint);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				points = rs.getInt(1);
				
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
		
		return points;
	}
	
	public List<Status> studentListWithSecondHighestPoint(String lecId, String subjCode, String groupId, int secondHighestPoint){
		
		List<Status> statusArray = new ArrayList<Status>();
		
		con = dao.getConnection();
		
		String sql = "select S.stud_name, S.stud_id, E.points from Student S join Enroll E on E.stud_id = S.stud_id join Class C on E.class_id = C.class_id where C.lec_id = ? and C.sub_code = ? and C.groupc_id = ? and E.points = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, lecId);
			stmt.setString(2, subjCode);
			stmt.setString(3, groupId);
			stmt.setInt(4, secondHighestPoint);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				Status status = new Status();
				status.setStudName(rs.getString("stud_name"));
				status.setStudId(rs.getString("stud_id"));
				status.setPoints(rs.getInt("points"));
				
				statusArray.add(status);
				
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
		
		
		return statusArray;
		
	}
	
	public int getThirdHighestPoint(String lecId, String subjCode, String groupId, int secondHighestPoint){	
		
		int points = 0;
		
		con = dao.getConnection();
		
		String sql = "select MAX(E.points) from Enroll E join Class C on E.class_id = C.class_id where C.lec_id = ? and C.sub_code = ? and C.groupc_id = ? and E.points < ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, lecId);
			stmt.setString(2, subjCode);
			stmt.setString(3, groupId);
			stmt.setInt(4, secondHighestPoint);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				points = rs.getInt(1);
				
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
		
		return points;
	}
	
	public List<Status> studentListWithThirdHighestPoint(String lecId, String subjCode, String groupId, int thirdHighestPoint){
		
		List<Status> statusArray = new ArrayList<Status>();
		
		con = dao.getConnection();
		
		String sql = "select S.stud_name, S.stud_id, E.points from Student S join Enroll E on E.stud_id = S.stud_id join Class C on E.class_id = C.class_id where C.lec_id = ? and C.sub_code = ? and C.groupc_id = ? and E.points = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, lecId);
			stmt.setString(2, subjCode);
			stmt.setString(3, groupId);
			stmt.setInt(4, thirdHighestPoint);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				Status status = new Status();
				status.setStudName(rs.getString("stud_name"));
				status.setStudId(rs.getString("stud_id"));
				status.setPoints(rs.getInt("points"));
				
				statusArray.add(status);
				
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
		
		
		return statusArray;
		
	}

}
