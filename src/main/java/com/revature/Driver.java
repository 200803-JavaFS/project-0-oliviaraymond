package com.revature;

import org.apache.logging.log4j.Logger;

import com.revature.daos.UserDAO;
import com.revature.models.User;
import com.revature.util.ConnectionUtility;
import com.revature.util.ConsoleUtility;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;

public class Driver {

	public static final Logger Log = LogManager.getLogger(Driver.class);
	
//	private static UserDAO dao = new UserDAO();

	public static void main(String[] args) {
		
		ConsoleUtility cons = new ConsoleUtility();
		cons.beginApp();
		Log.info("The application has started.");
		
		//consoleutil
		
		//to test and print out the UserDAO
		//List<User> list = dao.findAll();
		//for loop...
		
		

		// this tests the connection to database
		try(Connection conn = ConnectionUtility.getConnection()) {
			Log.info("Connection successful");
		}catch(SQLException e) {
			e.printStackTrace();
		}


		Log.info("The application has closed.");


	}

}
