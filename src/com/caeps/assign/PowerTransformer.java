package com.caeps.assign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ics.caps.PowerSystem.PowerTransformer;
import com.ics.caps.PowerSystem.Substation;


public class PowerTransformer extends ConductingEquipment{
	public Substation memberOfSubstation;
	
	public PowerTransformer(String rdfId,String name, Substation memberOfSubstation){
		super(rdfId,name);
		this.memberOfSubstation=memberOfSubstation;
	}
	
	ArrayList<PowerTransformer> getPowerTransformers(Document doc, Connection conn){
		ArrayList<PowerTransformer> powertransformers=new ArrayList<PowerTransformer>();
		String query = null;
		PreparedStatement preparedStmt;
		NodeList subList;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute("delete from PowerTransformer");
			subList = doc.getElementsByTagName("cim:PowerTransformer");
			for (int i = 0; i < subList.getLength(); i++) {
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd, "rdf:ID");
				String name = GetParam.getParam(nd, "cim:IdentifiedObject.name");
				String memOfSubstationId = GetParam.getParam(nd,"cim:Equipment.MemberOf_EquipmentContainer").substring(1);
				query = "insert into powerTransformers values (?,?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, memOfSubstationId);
				preparedStmt.execute();
				Substation substation = searchSubstation(substations,memOfSubstationId);
				PowerTransformer ab = new PowerTransformer(refId, name,substation);
				powertransformers.add(ab);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return powertransformers;
	}


}
