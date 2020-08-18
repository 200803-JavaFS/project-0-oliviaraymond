package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {
	
	public static Connection getConnection() throws SQLException {
		
		//For compatibility with other technologies/frameworks we need to register our Driver
		//Best practice to use this 
		try {
			Class.forName("org.postgresql.Driver");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url ="jdbc:postgresql://javafs200803.ceaalsuzinzr.us-east-2.rds.amazonaws.com:5432/bank";
		String username = "postgres";
		String password = "password";
		
		return DriverManager.getConnection(url, username, password);
	}
	


}
