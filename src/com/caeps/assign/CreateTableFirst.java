package com.caeps.assign;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableFirst {
	
	void createTables(Connection conn){
		
		System.out.println("Creating database...");
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "CREATE DATABASE IF NOT EXISTS PowerSystems";
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully...");
			// Connect to the created database STUDENTS and create table
					// REGISTRATION
			stmt.executeUpdate("use PowerSystem");
			
			sql = "CREATE TABLE IF NOT EXISTS BaseVoltage"
					+ "(rdfID VARCHAR(255) not NULL, " + " name VARCHAR(255), "
					+ " nominalValue VARCHAR(255), " + " PRIMARY KEY ( rdfID ))";
			stmt.executeUpdate(sql);
			System.out.println("Created table BaseVoltage in given database successfully");
			
			sql = "CREATE TABLE IF NOT EXISTS Substation"
					+ "(rdfID VARCHAR(255) not NULL, " + " name VARCHAR(255), "
					+ " regionRDFId VARCHAR(255), " + " PRIMARY KEY ( rdfID))";
			stmt.executeUpdate(sql);
			System.out.println("Created table Substation in given database successfully");
			
			sql = "CREATE TABLE IF NOT EXISTS VoltageLevel"
					+ "(rdfID VARCHAR(255) not NULL, " + " name VARCHAR(255), "
					+ " substationRDFId VARCHAR(255), " + "baseVoltageRDFId VARCHAR(255), "+" PRIMARY KEY ( rdfID),"+"FOREIGN KEY (substationRDFId) REFERENCES Substation(rdfID),"+"FOREIGN KEY (baseVoltageRDFId) REFERENCES BaseVoltage(rdfID))";
			stmt.executeUpdate(sql);
			System.out.println("Created table VoltageLevel in given database successfully");
			
			sql = "CREATE TABLE IF NOT EXISTS GeneratingUnit"
					+ "(rdfID VARCHAR(255) not NULL, " + " name VARCHAR(255), "
					+ " maxP DECIMAL(7,2), " + "minP DECIMAL(7,2), "+ "baseVoltageRDFId VARCHAR(255),"+" PRIMARY KEY ( rdfID))";
			stmt.executeUpdate(sql);
			System.out.println("Created table GeneratingUnit in given database successfully");
			
			sql = "CREATE TABLE IF NOT EXISTS SynchronousMachine"
					+ "(rdfID VARCHAR(255) not NULL, " + " name VARCHAR(255), "
					+ " ratedS DECIMAL(7,2), " +"GenratingUnitRDFId VARCHAR(255),"+"RegControlRDFId VARCHAR(255),"+"EquipmentRDFId VARCHAR(255),"+
					"BaseVoltageRDFId VARCHAR(255),"+ " PRIMARY KEY ( rdfID),"+
					"FOREIGN KEY (GenratingUnitRDFId) REFERENCES GeneratingUnit(rdfID),"+
					"FOREIGN KEY (RegControlRDFId) REFERENCES RegulatingControl(rdfID),"+
					"FOREIGN KEY (BaseVoltageRDFId) REFERENCES BaseVoltage(rdfID))";
			stmt.executeUpdate(sql);
			System.out.println("Created table SynchronousMachine in given database successfully");
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
