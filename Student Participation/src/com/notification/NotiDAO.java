package com.notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBconnection.DBconnect;

public class NotiDAO {
	
	static DBconnect dao = new DBconnect();
	static Connection con = null;
	
	public void insertNoti(String studId, String subjName, String reward, String todayDate){
		
		con = dao.getConnection();
		
		String sql = "insert into Notification (stud_id, sub_name, reward, noti_date) values (?, ?, ?, to_date(?, 'dd-MM-yyyy'))";
		
		PreparedStatement stmt = null;
		
		try {
			//System.out.println("ready to insert to database");
			stmt = con.prepareStatement(sql);
			stmt.setString(1, studId);
			stmt.setString(2, subjName);
			stmt.setString(3, reward);
			stmt.setString(4, todayDate);
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
	
	public List<Notification> getNoti(String studId){
		
		List<Notification> notiArray = new ArrayList<Notification>();
		
		con = dao.getConnection();
		
		String sql = "select sub_name, reward, to_char(noti_date, 'MM/dd/yyyy') as noti_date  from Notification where stud_id = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			//System.out.println("ready to insert to database");
			stmt = con.prepareStatement(sql);
			stmt.setString(1, studId);

			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				String subjName = rs.getString("sub_name");
				String reward = rs.getString("reward");
				String notiDate = rs.getString("noti_date");
				
				System.out.println(notiDate);
				
				Notification noti = new Notification();
				
				noti.setSubjName(subjName);
				noti.setReward(reward);
				noti.setNotiDate(notiDate);
				
				notiArray.add(noti);
			}
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
		
		
		
		return notiArray;
		
	}

}
