package com.caeps.assign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Disconnector extends ConductingEquipment {
	public EquipmentContainer memberOfEquipmentContainer;
	public BaseVoltage baseVoltage;
	public String state;
	
	public Disconnector(String rdfId, String name, String state, EquipmentContainer memberOfEquipmentContainer, BaseVoltage baseVoltage){
		super(rdfId,name);
		this.state = state;
		this.memberOfEquipmentContainer = memberOfEquipmentContainer;
		this.baseVoltage = baseVoltage;
	}

	static ArrayList<Disconnector> getDisconnectors(Document doc, Connection conn, ArrayList<EquipmentContainer> equipmentContainers, ArrayList<BaseVoltage> baseVoltages){

		ArrayList<Disconnector> disconnectors = new ArrayList<Disconnector>();
		String query = null;
		PreparedStatement preparedStmt;
		
		try {
			query="DELETE FROM Disconnector";
			preparedStmt=conn.prepareStatement(query);
			preparedStmt.execute();
			NodeList subList = doc.getElementsByTagName("cim:Disconnector");
			for (int i = 0; i < subList.getLength(); i++) {
				query = "INSERT INTO Disconnector VALUES (?,?,?,?,?)";
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
				Disconnector disconnectorObj = new Disconnector(refId, refName, st, equipmentContainer, baseVoltage);
				disconnectors.add(disconnectorObj);
				LoadXMLSQL.powerSystemResources.add(disconnectorObj);
				LoadXMLSQL.conductingEquipments.add(disconnectorObj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return disconnectors;
	}
}
