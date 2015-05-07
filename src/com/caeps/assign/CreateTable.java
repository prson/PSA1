package com.caeps.assign;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
	
	void createTables(Connection conn){
		
		System.out.println("Creating database...");
		Statement stmt;
		try {
			
			//create Database
			stmt = conn.createStatement();
			String sql = "CREATE DATABASE IF NOT EXISTS PowerSystem";
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully...");
			
			// Connect to the created database STUDENTS and create tables
			stmt.executeUpdate("use PowerSystem");
			
			//Create table BaseVoltage
			sql = "CREATE TABLE IF NOT EXISTS BaseVoltage"
					+ "(rdfId VARCHAR(255) not NULL, " + 
					"name VARCHAR(255), "
					+ " nominalValue VARCHAR(255), " + 
					" PRIMARY KEY ( rdfId ))";
			stmt.executeUpdate(sql);
			System.out.println("Created table BaseVoltage in given database successfully");
			
			//Create table Substation
			sql = "CREATE TABLE IF NOT EXISTS Substation" +
					"(rdfId VARCHAR(255) not NULL, " + 
					" name VARCHAR(255), " +
					" regionRDFId VARCHAR(255), " + 
					" PRIMARY KEY (rdfId))";
			stmt.executeUpdate(sql);
			System.out.println("Created table Substation in given database successfully");
			
			//Create table VoltageLevel
			sql = "CREATE TABLE IF NOT EXISTS VoltageLevel"+ 
					"(rdfId VARCHAR(255) not NULL, " + 
					" name VARCHAR(255), " + 
					" substationRDFId VARCHAR(255), " + 
					"baseVoltageRDFId VARCHAR(255), "+
					" PRIMARY KEY ( rdfId),"+
					"FOREIGN KEY (substationRDFId) REFERENCES Substation(rdfId),"+
					"FOREIGN KEY (baseVoltageRDFId) REFERENCES BaseVoltage(rdfId))";
			stmt.executeUpdate(sql);
			System.out.println("Created table VoltageLevel in given database successfully");
			
			//Create table GeneratingUnit
			sql = "CREATE TABLE IF NOT EXISTS GeneratingUnit" +
					"(rdfId VARCHAR(255) not NULL, " + 
					" name VARCHAR(255), "+
					" maxP DECIMAL(7,2), " + 
					"minP DECIMAL(7,2), "+ 
					"baseVoltageRDFId VARCHAR(255),"+
					" PRIMARY KEY ( rdfId))";
			stmt.executeUpdate(sql);
			System.out.println("Created table GeneratingUnit in given database successfully");
			
			// Create table RegulatingControl
						sql = "CREATE TABLE IF NOT EXISTS RegulatingControl " +
								"(rdfId VARCHAR(255), " +
								"name VARCHAR(255), " +
								"targetValue DECIMAL(7,2), " +
								"PRIMARY KEY (rdfId))";
						stmt.executeUpdate(sql);
						System.out.println("Created table RegulatingControl successfully...");
			
			// Create table SynchronousMachine
			sql = "CREATE TABLE IF NOT EXISTS SynchronousMachine" +
					"(rdfId VARCHAR(255) not NULL, " + 
					" name VARCHAR(255), " +
					" ratedS DECIMAL(7,2), " +
					"GenratingUnitRDFId VARCHAR(255),"+
					"RegControlRDFId VARCHAR(255),"+
					"EquipmentRDFId VARCHAR(255),"+	
					"BaseVoltageRDFId VARCHAR(255),"+ 
					" PRIMARY KEY ( rdfId),"+
					"FOREIGN KEY (GenratingUnitRDFId) REFERENCES GeneratingUnit(rdfId),"+
					"FOREIGN KEY (RegControlRDFId) REFERENCES RegulatingControl(rdfId),"+
					"FOREIGN KEY (BaseVoltageRDFId) REFERENCES BaseVoltage(rdfId))";
			stmt.executeUpdate(sql);
			System.out.println("Created table SynchronousMachine in given database successfully");
			
			// Create table Analog
			sql = "CREATE TABLE IF NOT EXISTS Analog " +
					"(rdfId VARCHAR(255), " +
					"name VARCHAR(255), " +
					"normalValue DECIMAL(7,2), " +
					"measurementType VARCHAR(255), " +
					"memberRdfId VARCHAR(255), " +
					"PRIMARY KEY (rdfId))";
			stmt.executeUpdate(sql);
			System.out.println("Created table Analog successfully...");

			// Create table Disconnector
			sql = "CREATE TABLE IF NOT EXISTS Disconnector " +
					"(rdfId VARCHAR(255), " +
					"name VARCHAR(255), " +
					"state VARCHAR(255), " +
					"equipmentRdfId VARCHAR(255), " +
					"baseVoltageRdfId VARCHAR(255), " +
					"PRIMARY KEY (rdfId), " +
					"FOREIGN KEY (baseVoltageRdfID) REFERENCES BaseVoltage(rdfID))";
			stmt.executeUpdate(sql);
			System.out.println("Created table Disconnector successfully...");

			// Create table Breaker
			sql = "CREATE TABLE IF NOT EXISTS Breaker " +
					"(rdfId VARCHAR(255), " +
					"name VARCHAR(255), " +
					"state VARCHAR(255), " +
					"equipmentRdfId VARCHAR(255), " +
					"baseVoltageRdfId VARCHAR(255), " +
					"PRIMARY KEY (rdfId), " +
					"FOREIGN KEY (baseVoltageRdfID) REFERENCES BaseVoltage(rdfID))";
			stmt.executeUpdate(sql);
			System.out.println("Created table Breaker successfully...");
			
			// Create table PowerTransformer
			sql = "CREATE TABLE IF NOT EXISTS PowerTransformer " +
					"(rdfId VARCHAR(255), " +
					"name VARCHAR(255), " +
					"equipmentRdfId VARCHAR(255), " +
					"PRIMARY KEY (rdfId))";
			stmt.executeUpdate(sql);
			System.out.println("Created table PowerTransformer successfully...");

			// Create table TransformerWinding
			sql = "CREATE TABLE IF NOT EXISTS TransformerWinding " +
					"(rdfId VARCHAR(255), " +
					"name VARCHAR(255), " +
					"r DECIMAL(5,4), " +
					"x DECIMAL(5,4), " +
					"transformerRdfId VARCHAR(255), " +
					"baseVoltageRdfId VARCHAR(255), " +
					"PRIMARY KEY (rdfId), " +
					"FOREIGN KEY (transformerRdfID) REFERENCES PowerTransformer(rdfID), " +
					"FOREIGN KEY (baseVoltageRdfID) REFERENCES BaseVoltage(rdfID))";
			stmt.executeUpdate(sql);
			System.out.println("Created table TransformerWinding successfully...");

			// Create table Load
			sql = "CREATE TABLE IF NOT EXISTS Load " +
					"(rdfId VARCHAR(255), " +
					"name VARCHAR(255), " +
					"pFixed DECIMAL(7,2), " +
					"qFixed DECIMAL(7,2), " +
					"equipmentRdfId VARCHAR(255), " +
					"baseVoltageRdfId VARCHAR(255), " +
					"PRIMARY KEY (rdfId), " +
					"FOREIGN KEY (baseVoltageRdfID) REFERENCES BaseVoltage(rdfID))";
			stmt.executeUpdate(sql);
			System.out.println("Created table Load successfully...");
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
