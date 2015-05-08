package com.caeps.assign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Load extends ConductingEquipment{
	
	public double pfixed;
	public double qfixed;
	public EquipmentContainer memberOfEquipmentContainer;
	public BaseVoltage baseVoltage;
	
	public Load(String rdfId,String name, double pfixed, double qfixed, EquipmentContainer memberOfEquipmentContainer, BaseVoltage baseVoltage){
		super(rdfId,name);
		this.pfixed=pfixed;
		this.qfixed=qfixed;
		this.memberOfEquipmentContainer=memberOfEquipmentContainer;
		this.baseVoltage=baseVoltage;
	}
	
	static ArrayList<Load> getLoad(Document doc, Connection conn, ArrayList<EquipmentContainer> equipmentcontainers, ArrayList<BaseVoltage> baseVoltages){
		ArrayList<Load> loads=new ArrayList<Load>();
		String query = null;
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute("delete from nonconformloads");
			NodeList subList = doc.getElementsByTagName("cim:NonConformLoad");
			for (int i = 0; i < subList.getLength(); i++) {
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd, "rdf:ID");
				String name = GetParam.getParam(nd, "cim:IdentifiedObject.name");
				double pfixed = Double.parseDouble(GetParam.getParam(nd,
						"cim:EnergyConsumer.pfixed"));
				double qfixed = Double.parseDouble(GetParam.getParam(nd,
						"cim:EnergyConsumer.qfixed"));
				String memOfEquipmentContainer = GetParam.getParam(nd,
						"cim:Equipment.MemberOf_EquipmentContainer").substring(
						1);
				String baseVoltageId = GetParam.getParam(nd,
						"cim:ConductingEquipment.BaseVoltage").substring(1);
				query = "insert into nonconformloads values (?,?,?,?,?,?,?,?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, name);
				preparedStmt.setDouble(3, pfixed);
				preparedStmt.setDouble(4, qfixed);
				preparedStmt.setString(5, memOfEquipmentContainer);
				preparedStmt.setString(6, baseVoltageId);
				preparedStmt.execute();
				EquipmentContainer equipmentContainer = EquipmentContainer.searchEquipmentContainer(
						equipmentcontainers, memOfEquipmentContainer);
				BaseVoltage baseVoltage = BaseVoltage.searchBaseVoltage(baseVoltages,
						baseVoltageId);
				Load ab = new Load(refId, name, 
						pfixed, qfixed, equipmentContainer, baseVoltage);
				loads.add(ab);
				LoadXMLSQL.powerSystemResources.add(ab);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loads;
	}
}
