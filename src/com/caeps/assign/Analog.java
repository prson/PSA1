package com.caeps.assign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class Analog.
 */
public class Analog extends IdentifiedObject {

	/** The normal value. */
	public double normalValue;
	
	/** The measurement type. */
	public String measurementType;
	
	/** The member of power system resource. */
	public PowerSystemResource memberOfPowerSystemResource;
	
	/** The logger. */
	static Logger logger = Logger.getLogger(Analog.class);
	
	/**
	 * Instantiates a new analog.
	 *
	 * @param rdfId the rdf id
	 * @param name the name
	 * @param normalValue the normal value
	 * @param measurementType the measurement type
	 * @param memberOfPowerSystemResource the member of power system resource
	 */
	public Analog(String rdfId, String name, double normalValue, String measurementType, PowerSystemResource memberOfPowerSystemResource) {
		super(rdfId, name);
		this.normalValue = normalValue;
		this.measurementType = measurementType;
		this.memberOfPowerSystemResource = memberOfPowerSystemResource;
	}

	/**
	 * Gets the analogs.
	 *
	 * @param doc the doc
	 * @param conn the conn
	 * @param powerSystemResources the power system resources
	 * @return the analogs
	 */
	static ArrayList<Analog> getAnalogs(Document doc, Connection conn, ArrayList<PowerSystemResource> powerSystemResources) {

		ArrayList<Analog> analogs = new ArrayList<Analog>();
		String query = null;
		PreparedStatement preparedStmt;
		
		try {
			query="DELETE FROM Analog";
			preparedStmt=conn.prepareStatement(query);
			preparedStmt.execute();
			NodeList subList = doc.getElementsByTagName("cim:Analog");
			for (int i = 0; i < subList.getLength(); i++) {
				query = "INSERT INTO Analog VALUES (?,?,?,?,?)";
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd,"rdf:ID");
				String refName = GetParam.getParam(nd,"cim:IdentifiedObject.name");
				double normalVal = Double.parseDouble(GetParam.getParam(nd,"cim:Analog.normalValue"));
				String measType = GetParam.getParam(nd,"cim:Measurement.measurementType");
				String memOfPowerSysResId = GetParam.getParam(nd,"cim:Measurement.MemberOf_PSR").substring(1);
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, refName);
				preparedStmt.setDouble(3, normalVal);
				preparedStmt.setString(4, measType);
				preparedStmt.setString(5, memOfPowerSysResId);				
				preparedStmt.execute();
				PowerSystemResource powerSysRes = PowerSystemResource.searchPowerSystemResource(powerSystemResources, memOfPowerSysResId);
				Analog analogObj = new Analog(refId, refName, normalVal, measType, powerSysRes);
				analogs.add(analogObj);
			}
			logger.debug("Read the analog contents from the XML file, loaded to the database");
		} catch (SQLException e) {
			logger.error("Error in loading analog details into the database",e);
//			e.printStackTrace();
		}
		return analogs;
	}
}
