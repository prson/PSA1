package com.caeps.assign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Breaker extends ConductingEquipment {
	public EquipmentContainer memberOfEquipmentContainer;
	public BaseVoltage baseVoltage;
	public String state;
	
	public Breaker(String rdfId,String name, String state, EquipmentContainer memberOfEquipmentContainer, BaseVoltage baseVoltage){
		super(rdfId,name);
		this.state = state;
		this.memberOfEquipmentContainer = memberOfEquipmentContainer;
		this.baseVoltage = baseVoltage;
	}

	static ArrayList<Breaker> getBreakers(Document doc, Connection conn, ArrayList<EquipmentContainer> equipmentContainers, ArrayList<BaseVoltage> baseVoltages){

		ArrayList<Breaker> breakers = new ArrayList<Breaker>();
		String query = null;
		PreparedStatement preparedStmt;
		
		try {
			query="DELETE FROM Breaker";
			preparedStmt=conn.prepareStatement(query);
			preparedStmt.execute();
			NodeList subList = doc.getElementsByTagName("cim:Breaker");
			for (int i = 0; i < subList.getLength(); i++) {
				query = "INSERT INTO Breaker VALUES (?,?,?,?,?)";
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd,"rdf:ID");
				String refName = GetParam.getParam(nd,"cim:IdentifiedObject.name");
				String st = GetParam.getParam(nd,"cim:Switch.normalOpen");
				String memOfEquipmentContainer = GetParam.getParam(nd,"cim:Equipment.MemberOf_EquipmentContainer").substring(1);
				String baseVoltageId = GetParam.getParam(nd,"cim:ConductingEquipment.BaseVoltage").substring(1);
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, refName);
				preparedStmt.setString(3, st);
				preparedStmt.setString(4, memOfEquipmentContainer);
				preparedStmt.setString(5, baseVoltageId);				
				preparedStmt.execute();
				EquipmentContainer equipmentContainer = EquipmentContainer.searchEquipmentContainer(equipmentContainers, memOfEquipmentContainer);
				BaseVoltage baseVoltage = BaseVoltage.searchBaseVoltage(baseVoltages,baseVoltageId);
				Breaker breakerObj = new Breaker(refId, refName, st, equipmentContainer, baseVoltage);
				breakers.add(breakerObj);
				LoadXMLSQL.powerSystemResources.add(breakerObj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return breakers;
	}

}
