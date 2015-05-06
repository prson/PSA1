package com.caeps.assign;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {
	
	public ConnectToDB(){}
	
	// establishing a connection to the database
	Connection establishConnection(String url, String username, String password) {
		try {
			System.out.println("Loading driver...");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(
					"Cannot find the driver in the classpath!", e);
		}

		Connection connection = null;
		try {
			System.out.println("Connecting database...");
			connection = (Connection) DriverManager.getConnection(url,username, password);
			System.out.println("Database connected!");
			return connection;
		} 
		catch (SQLException e) {
			throw new RuntimeException("Cannot connect the database!", e);
		} 
		finally {
		}
	}
}
