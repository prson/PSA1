package com.caeps.assign;
import java.sql.*;

public class CreateTableSecond {

	// Create table Analog
	sql = "CREATE TABLE IF NOT EXISTS Analog " +
			"(rdfId VARCHAR(255), " +
			"name VARCHAR(255), " +
			"normalValue DECIMAL(7,2), " +
			"measurementType VARCHAR(255), " +
			"memberRdfId VARCHAR(255), " +
			"PRIMARY KEY (rdfId), " +
			"FOREIGN KEY (memberRdfID) REFERENCES PowerSystemResource(rdfID))";
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

	// Create table PowerTransformer
	sql = "CREATE TABLE IF NOT EXISTS PowerTransformer " +
			"(rdfId VARCHAR(255), " +
			"name VARCHAR(255), " +
			"equipmentRdfId VARCHAR(255), " +
			"PRIMARY KEY (rdfId))";
	stmt.executeUpdate(sql);
	System.out.println("Created table PowerTransformer successfully...");

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

	// Create table RegulatingControl
	sql = "CREATE TABLE IF NOT EXISTS RegulatingControl " +
			"(rdfId VARCHAR(255), " +
			"name VARCHAR(255), " +
			"targetValue DECIMAL(7,2), " +
			"PRIMARY KEY (rdfId))";
	stmt.executeUpdate(sql);
	System.out.println("Created table RegulatingControl successfully...");

}
