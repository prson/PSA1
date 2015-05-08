package com.caeps.assign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Substation extends EquipmentContainer {

	public String region_rdfID;

	public Substation(String rdfId, String n, String region_rdfId){
		super(rdfId,n);
		region_rdfID = region_rdfId;
	}
	
	static ArrayList<Substation> getSubstations(Document doc, Connection conn){
		ArrayList<Substation> substations=new ArrayList<Substation>();
		String query = null;
		PreparedStatement preparedStmt;
		NodeList subList;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute("delete from Substation");
			subList = doc.getElementsByTagName("cim:Substation");
			for (int i = 0; i < subList.getLength(); i++) {
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd, "rdf:ID");
				String name = GetParam.getParam(nd, "cim:IdentifiedObject.name");
				String regionId = GetParam.getParam(nd, "cim:Substation.Region").substring(1);
				query = "INSERT INTO Substation VALUES (?,?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, regionId);
				preparedStmt.execute();
				Substation ab = new Substation(refId, name, regionId);
				substations.add(ab);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return substations;
	}
	
	static Substation searchSubstation(ArrayList<Substation> substationsList,
			String rdfId) {
		Substation objectFound = null;
		for (Substation objIt : substationsList) {
			if (objIt.rdfID.equals(rdfId)) {
				objectFound = objIt;
				break;
			}
		}
		return objectFound;
	}
}
