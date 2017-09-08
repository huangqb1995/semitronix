package util;

import java.sql.DriverManager;
import com.mysql.jdbc.Connection;

public class DBConnect {
	
	private static final String driver = "com.mysql.jdbc.Driver"; 
	private static final String url="jdbc:mysql://localhost:3306/talldata?useUnicode=true&characterEncoding=UTF-8"; 
	private static final String username="root";
	private static final String password="0710";
    
	private static Connection conn=null;
	
	static{
		try{
			Class.forName(driver);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws Exception {
		if(conn == null){
			conn = (Connection) DriverManager.getConnection(url, username, password);
			return conn;
		}
		return conn;
	}
}
