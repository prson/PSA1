package com.caeps.systemcomponents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.caeps.gui.PSAnalysisPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class Disconnector.
 */
public class Disconnector extends ConductingEquipment {
	
	/** The member of equipment container. */
	public EquipmentContainer memberOfEquipmentContainer;
	
	/** The base voltage. */
	public BaseVoltage baseVoltage;
	
	/** The state. */
	public String state;
	
	/** The logger. */
	static Logger logger = Logger.getLogger(Disconnector.class);
	
	/**
	 * Instantiates a new disconnector.
	 *
	 * @param rdfId the rdf id
	 * @param name the name
	 * @param state the state
	 * @param memberOfEquipmentContainer the member of equipment container
	 * @param baseVoltage the base voltage
	 */
	public Disconnector(String rdfId, String name, String state, 
			EquipmentContainer memberOfEquipmentContainer, BaseVoltage baseVoltage){
		super(rdfId, name);
		this.state = state;
		this.memberOfEquipmentContainer = memberOfEquipmentContainer;
		this.baseVoltage = baseVoltage;
	}

	/**
	 * Gets the disconnectors.
	 *
	 * @param doc the doc built from the CIM XML File
	 * @param conn the connection object to the database
	 * @param equipmentContainers the equipment containers list in power system
	 * @param baseVoltages the base voltages in the power system
	 * @return the array list of disconnectors in the power system
	 */
	static ArrayList<Disconnector> getDisconnectors(Document doc, Connection conn, ArrayList<EquipmentContainer> equipmentContainers, ArrayList<BaseVoltage> baseVoltages){

		ArrayList<Disconnector> disconnectors = new ArrayList<Disconnector>();
		String query = null;
		PreparedStatement preparedStmt;
		
		try {
			query = "DELETE FROM Disconnector";
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
			NodeList subList = doc.getElementsByTagName("cim:Disconnector");
			for (int i = 0; i < subList.getLength(); i++) {
				query = "INSERT INTO Disconnector VALUES (?,?,?,?,?)";
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd, "rdf:ID");
				String refName = GetParam.getParam(nd, "cim:IdentifiedObject.name");
				String st = GetParam.getParam(nd, "cim:Switch.normalOpen");
				String memOfEquipmentContainer = GetParam.getParam(nd,
						"cim:Equipment.MemberOf_EquipmentContainer").substring(1);
				String baseVoltageId = GetParam.getParam(nd,
						"cim:ConductingEquipment.BaseVoltage").substring(1);
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, refName);
				preparedStmt.setString(3, st);
				preparedStmt.setString(4, memOfEquipmentContainer);
				preparedStmt.setString(5, baseVoltageId);				
				preparedStmt.execute();
				EquipmentContainer equipmentContainer = EquipmentContainer.
						searchEquipmentContainer(equipmentContainers, 
								memOfEquipmentContainer);
				BaseVoltage baseVoltage = BaseVoltage.searchBaseVoltage(
						baseVoltages,baseVoltageId);
				Disconnector disconnectorObj = new Disconnector(refId, refName, st,
						equipmentContainer, baseVoltage);
				disconnectors.add(disconnectorObj);
				LoadXMLSQL.powerSystemResources.add(disconnectorObj);
				LoadXMLSQL.conductingEquipments.add(disconnectorObj);
			}
		} catch (SQLException e) {
			logger.error("SQL Exception Error in loading disconnector details into the database",e);
			PSAnalysisPanel.consoleArea.append("\nSQL Exception Error in loading disconnector details into the database. Check logs for more details");
		}
		return disconnectors;
	}
}
