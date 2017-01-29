package DBconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {
	
	public Connection getConnection(){
		
		String connectionURL = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection con = null;
		
		try{
			
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			con = DriverManager.getConnection(connectionURL,"fypdatabase","fypdatabase");
				
		}catch (Exception e){
			e.printStackTrace();
		}	
	
		return con;
	}
	
	public void closeConnection(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
