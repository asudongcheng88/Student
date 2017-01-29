package com.reward;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBconnection.DBconnect;

public class RewardDAO {
	
	static DBconnect dao = new DBconnect();
	static Connection con = null;
	
	public void addReward(String lecId, String rewardType, int rewardPoint){
		
		//System.out.println("enter method");
		con = dao.getConnection();
		
		String sql = "insert into Reward (lec_id, reward_type, reward_value) values (?, ?, ?)";
		
		PreparedStatement stmt = null;
		
		try {
			//System.out.println("ready to insert to database");
			stmt = con.prepareStatement(sql);
			stmt.setString(1, lecId);
			stmt.setString(2, rewardType);
			stmt.setInt(3, rewardPoint);
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
	
	public List<Reward> getRewardList(String lecId){
		
		List<Reward> rewardArray = new ArrayList<Reward>();
		
		
		con = dao.getConnection();
		
		String sql = "select * from Reward where lec_id = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, lecId);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				Reward reward = new Reward();
				reward.setRewardType(rs.getString("reward_type"));
				reward.setRewardPoint(rs.getInt("reward_value"));
				
				rewardArray.add(reward);
				
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
		
		return rewardArray;
	}
	
	public int getRewardValue(String lecId, String rewardType){
		
		int value = 0;
		
		con = dao.getConnection();
		
		String sql = "select reward_value from Reward where lec_id = ? and reward_type = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, lecId);
			stmt.setString(2, rewardType);
			rs = stmt.executeQuery();
		
			while(rs.next()){
				
				value = rs.getInt("reward_value");
				
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
		return value;
	}
	
	public boolean isExist(String studId, String subjCode){
		
		con = dao.getConnection();
		
		String sql = "select stud_id, sub_code from Points where stud_id = ? and sub_code = ?" ;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			stmt = con.prepareStatement(sql);
			
			
			
			stmt.setString(1, studId);
			stmt.setString(2, subjCode);
			
			rs = stmt.executeQuery();
			
			dao.closeConnection(con);
			
			
			if(rs.next()){
				stmt.close();
				rs.close();
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false; 
		
	}
	
	public void giveStudentReward(String studId, int classId, int rewardPoint){
		
		
		con = dao.getConnection();
		
		String sql = "update Enroll set points = (points + ?) where stud_id = ? and class_id = ?" ;
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, rewardPoint);
			stmt.setString(2, studId);
			stmt.setInt(3, classId);
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
	
	
	public List<Reward> getRewardListForStudent(String studId, String subjCode){
		
		List<Reward> rewardArray = new ArrayList<Reward>();
		
		
		con = dao.getConnection();
		
		String sql = "select * from Reward where lec_id = (select C.lec_id from Class C join Enroll E on C.class_id = E.class_id where E.stud_id = ? and C.sub_code = ?)";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, studId);
			stmt.setString(2, subjCode);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				Reward reward = new Reward();
				reward.setRewardType(rs.getString("reward_type"));
				reward.setRewardPoint(rs.getInt("reward_value"));
				
				rewardArray.add(reward);
				
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
		
		return rewardArray;
	}

	public boolean rewardIsExist(String rewardType) {
		
		con = dao.getConnection();
		
		String sql = "select * from fix_reward where fix_reward_type = ?";
		boolean exist = false;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, rewardType);
			rs = stmt.executeQuery();
			System.out.println("hey");
			while(rs.next()){
				System.out.println(rs.getString("fix_reward_type"));
				exist = true;
				
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			dao.closeConnection(con);
			
		}
		
		
		return exist;
	}
	
	
	public List<Reward> getFixRewardList(){
		
		List<Reward> rewardArray = new ArrayList<Reward>();
		
		
		con = dao.getConnection();
		
		String sql = "select * from fix_reward";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				Reward reward = new Reward();
				reward.setRewardType(rs.getString("fix_reward_type"));
				reward.setRewardPoint(rs.getInt("fix_reward_point"));
				
				rewardArray.add(reward);
				
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
		
		return rewardArray;
	}
	
	
}
