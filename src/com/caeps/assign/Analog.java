package com.caeps.assign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Analog extends ConductingEquipment {

	public double normalValue;
	public String measurementType;
	public PowerSystemResource memberOfPowerSystemResource;
	
	public Analog(String rdfId, String name, double normalValue, String measurementType, PowerSystemResource memberOfPowerSystemResource) {
		super(rdfId, name);
		this.normalValue = normalValue;
		this.measurementType = measurementType;
		this.memberOfPowerSystemResource = memberOfPowerSystemResource;
	}

	static ArrayList<Analog> getAnalogs(Document doc, Connection conn, ArrayList<PowerSystemResource> powerSystemResources) {

		ArrayList<Analog> analogs = new ArrayList<Analog>();
		String query = null;
		PreparedStatement preparedStmt;
		
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute("DELETE FROM Analog");
			NodeList subList = doc.getElementsByTagName("cim:Analog");
			for (int i = 0; i < subList.getLength(); i++) {
				query = "INSERT INTO Analog VALUES (?,?,?,?,?)";
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd,"rdf:ID");
				String refName = GetParam.getParam(nd,"cim:IdentifiedObject.name");
				double normalVal = Double.parseDouble(GetParam.getParam(nd,"cim:Analog.normalValue"));
				String measType = GetParam.getParam(nd,"cim:Analog.measurementType");
				String memOfPowerSysResId = GetParam.getParam(nd,"cim:Analog.memberOfPowerSystemResource");
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return analogs;
	}
}
