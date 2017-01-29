package com.group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBconnection.DBconnect;

public class GroupDAO {
	
	static DBconnect dao = new DBconnect();
	static Connection con = null;
	
	public List<Group> groupList(){
		
		List<Group> arrayGroup = new ArrayList<Group>();
		
		con = dao.getConnection();
		
		String sql = "select * from Groupc";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				String groupId = rs.getString("groupc_id");
				Group group = new Group();
				group.setGroupId(groupId);
				
				arrayGroup.add(group);
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
		
		return arrayGroup;
	}
	
	public List<Group> lecSubjectGroupList(String lecId, String subjName){
		
		List<Group> groupArray = new ArrayList<Group>();
		
		con = dao.getConnection();
		
		String sql = "select C.groupc_id from Class C join Subject S on C.sub_code = S.sub_code where C.lec_id = ? and S.sub_name = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, lecId);
			stmt.setString(2, subjName);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				Group group = new Group();
				group.setGroupId(rs.getString("groupc_id"));
				groupArray.add(group);
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
		
		
		return groupArray;
		
	}
	
	
	public List<Group> studentGroupList(String studId, String subjName){
		//System.out.println("student group list");
		List<Group> groupArray = new ArrayList<Group>();
		
		con = dao.getConnection();
		
		String sql = "select C.groupc_id from Class C join Subject S on C.sub_code = S.sub_code join Enroll E on E.class_id = C.class_id where E.stud_id = ? and S.sub_name = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, studId);
			stmt.setString(2, subjName);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				//System.out.println("Group = "+rs.getString("groupc_id"));
				Group group = new Group();
				group.setGroupId(rs.getString("groupc_id"));
				groupArray.add(group);
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
		
		
		return groupArray;
		
	}
	
	public String getStudentGroupId(String studId, String subjName){
		
		String groupId = "";
		
		con = dao.getConnection();
		
		String sql = "select C.groupc_id from Class C join Subject S on C.sub_code = S.sub_code join Enroll E on E.class_id = C.class_id where E.stud_id = ? and S.sub_name = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, studId);
			stmt.setString(2, subjName);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				groupId = rs.getString("groupc_id");
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
		

		return groupId;
		
		
	}

}
