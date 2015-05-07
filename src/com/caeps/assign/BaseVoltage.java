package com.caeps.assign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class BaseVoltage extends IdentifiedObject{

	public double nominalValue;

	public BaseVoltage(String rdfId, String n, double nomVal){
		super(rdfId,n);
		nominalValue = nomVal;
	}
	
	ArrayList<BaseVoltage> getBaseVoltages(Document doc, Connection conn){
		ArrayList<BaseVoltage> baseVoltages=new ArrayList<BaseVoltage>();
		String query = null;
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute("delete from BaseVoltages");
			NodeList subList = doc.getElementsByTagName("cim:BaseVoltage");
			for (int i = 0; i < subList.getLength(); i++) {
				query = "insert into BaseVoltages values (?,?,?)";
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd,"rdf:ID");
				String refName = GetParam.getParam(nd,"cim:IdentifiedObject.name");
				double nominalVoltage = Double.parseDouble(GetParam.getParam(nd,"cim:BaseVoltage.nominalVoltage"));
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, refName);
				preparedStmt.setDouble(3, nominalVoltage);
				preparedStmt.execute();
				BaseVoltage baseVoltageObj = new BaseVoltage(refId, refName, nominalVoltage);
				baseVoltages.add(baseVoltageObj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baseVoltages;
	}
}