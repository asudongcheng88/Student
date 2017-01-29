package com.enroll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBconnection.DBconnect;

public class EnrollDAO {

	static DBconnect dao = new DBconnect();
	static Connection con = null;
	
	/*----get class id for student enroll to class----*/
	
	public int getClassForStudent(String subjCode, String groupId){
		
		int classId = 0;
		
		con = dao.getConnection();
		
		String sql = "select class_id from Class where sub_code = ? and groupc_id = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, subjCode);
			stmt.setString(2, groupId);
			
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
	
	/*-----------student registering class-------------*/
	
	public void enrollClassForStudent(String studId, int classId){
		//System.out.println("lol");
		con = dao.getConnection();
		
		String sql = "insert into Enroll (stud_id, class_id, req_leave, points) values (?, ?, ?, ?)";
		
		//int req_leave = 0;
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, studId);
			stmt.setInt(2, classId);
			stmt.setInt(3, 0);
			stmt.setInt(4, 0);
			
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
	
	public void requestLeave(String studId, int classId){
		
		con = dao.getConnection();
		
		String sql = "update Enroll set req_leave = '1' where stud_id = ? and class_id = ?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, studId);
			stmt.setInt(2, classId);

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
	
	public List<Enroll> showLeaveRequest(String lecId){
		
		List<Enroll> enrollArray = new ArrayList<Enroll>();
		
		con = dao.getConnection();
		
		String sql = "select E.stud_id, St.stud_name, C.groupc_id, S.sub_code, S.sub_name, C.class_id from Enroll E join Class C on E.class_id = C.class_id join Subject S on C.sub_code = S.sub_code join Student St on St.stud_id = E.stud_id where C.lec_id = ? and E.req_leave=?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, lecId);
			stmt.setInt(2, 1);
			
			rs = stmt.executeQuery();

			while(rs.next()){
				//System.out.println("student name = " + rs.getString("stud_name"));
				Enroll enroll = new Enroll();
				
				enroll.setStudId(rs.getString("stud_id"));
				enroll.setStudName(rs.getString("stud_name"));
				enroll.setGroupId(rs.getString("groupc_id"));
				enroll.setSubjCode(rs.getString("sub_code"));
				enroll.setSubjName(rs.getString("sub_name"));
				enroll.setClassId(rs.getString("class_id"));
				
				enrollArray.add(enroll);
				
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
		
		return enrollArray;

	}
	
	public void approveRequest(String studId, int classId){
		//System.out.println(classId);
		con = dao.getConnection();
		
		String sql = "delete from Enroll where class_id=? and stud_id=?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, classId);
			stmt.setString(2, studId);

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
	
	public List<Enroll> studentPointList(String subjCode, String groupId){
		
		List<Enroll> enrollArray = new ArrayList<Enroll>();
		
		con = dao.getConnection();
		
		String sql = "select S.stud_name, S.stud_id, E.points from Student S join Enroll E on S.stud_id = E.stud_id join Class C on E.class_id = C.class_id where C.sub_code = ? and C.groupc_id = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, subjCode);
			stmt.setString(2, groupId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				//System.out.println("hey "+rs.getString("stud_name"));
				
				Enroll enroll = new Enroll();
				
				enroll.setStudName(rs.getString("stud_name"));
				enroll.setStudId(rs.getString("stud_id"));
				enroll.setPoints(rs.getInt("points"));
				
				enrollArray.add(enroll);
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
		
		return enrollArray;
	}
	
	boolean gotDropRequest(String lecId){
		
		con = dao.getConnection();
		
		String sql = "select * from Enroll E join Class C on E.class_id = C.class_id where C.lec_id = ? and E.req_leave = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, lecId);
			stmt.setInt(2, 1);
			
			rs = stmt.executeQuery();
			
			boolean exist = rs.next();
			
			if(exist){
				
				return true;
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
		return false;
	}
	
	
}
