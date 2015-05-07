package com.caeps.assign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class RegulatingControl extends ConductingEquipment{

	public double targetValue;
	
	public RegulatingControl(String rdfId,String name, double targetValue){
		super(rdfId,name);
		this.targetValue=targetValue;
	}
	
	ArrayList<RegulatingControl> getRegulatingControl(Document doc, Connection conn){
		ArrayList<RegulatingControl> regulatingControls=new ArrayList<RegulatingControl>();
		String query = null;
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute("delete from regulatingControls");
			NodeList subList = doc.getElementsByTagName("cim:RegulatingControl");
			for (int i = 0; i < subList.getLength(); i++) {
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd, "rdf:ID");
				String name = GetParam.getParam(nd, "cim:IdentifiedObject.name");
				Double targetValue = Double.parseDouble(GetParam.getParam(nd,"cim:RegulatingControl.targetValue"));
				query = "insert into regulatingControls values (?,?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, name);
				preparedStmt.setDouble(3, targetValue);
				preparedStmt.execute();
				RegulatingControl ab = new RegulatingControl(refId, name,targetValue);
				regulatingControls.add(ab);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return regulatingControls;
	}
}
