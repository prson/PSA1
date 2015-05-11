package com.caeps.assign;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ConnectToDB {
	Logger logger = Logger.getLogger(ConnectToDB.class);

	public ConnectToDB() {
	}

	// establishing a connection to the database
	Connection establishConnection(String url, String username, String password) {

		try {
			logger.debug("Loading driver...");
			Class.forName("com.mysql.jdbc.Driver");
			logger.debug("Driver loaded!");
		} catch (ClassNotFoundException e) {
			logger.error("Cannot find the driver in the classpath!", e);
		}

		Connection connection = null;
		try {
			logger.debug("Connecting database...");
			connection = (Connection) DriverManager.getConnection(url,
					username, password);
			logger.debug("Database connected!");
			return connection;
		} catch (SQLException e) {
			logger.error("Cannot connect the database!", e);
			throw new RuntimeException("Cannot connect the database!", e);
		} finally {
		}
	}
}
