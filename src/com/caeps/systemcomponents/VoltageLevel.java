package com.caeps.systemcomponents;

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
 * The Class VoltageLevel.
 */
public class VoltageLevel extends EquipmentContainer {

	/** The base voltage. */
	public BaseVoltage baseVoltage;
	
	/** The member of substation. */
	public Substation memberOfSubstation;

	/** The logger. */
	private static Logger logger = Logger.getLogger(VoltageLevel.class);

	/**
	 * Instantiates a new voltage level object.
	 *
	 * @param rdfId the rdf id
	 * @param n the name
	 * @param baseVoltage the base voltage
	 * @param memberOfSubstation the member of substation
	 */
	public VoltageLevel(String rdfId, String n, BaseVoltage baseVoltage,
			Substation memberOfSubstation){
		super(rdfId, n);
		this.baseVoltage = baseVoltage;
		this.memberOfSubstation = memberOfSubstation;
	}
	
	/**
	 * Gets all voltage level objects from the CIM file and stores them in the 
	 * internal data structure and database.
	 *
	 * @param doc the document
	 * @param conn the connection
	 * @param substations the substations
	 * @param baseVoltages the base voltages
	 * @return the voltage level
	 */
	static ArrayList<VoltageLevel> getVoltageLevel(Document doc, Connection conn,
			ArrayList<Substation> substations, ArrayList<BaseVoltage> baseVoltages){
		ArrayList<VoltageLevel> voltageLevels = new ArrayList<VoltageLevel>();
		String query = null;
		PreparedStatement preparedStmt;
		NodeList subList;
		try {
			subList = doc.getElementsByTagName("cim:VoltageLevel");
			for (int i = 0; i < subList.getLength(); i++) {
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd, "rdf:ID");
				String refName = GetParam.getParam(nd, "cim:IdentifiedObject.name");
				String memOfSubstationID = GetParam.getParam(nd,
						"cim:VoltageLevel.MemberOf_Substation").substring(1);
				String baseVoltageId = GetParam.getParam(nd,
						"cim:VoltageLevel.BaseVoltage").substring(1);
				query = "insert into VoltageLevel values (?,?,?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, refName);
				preparedStmt.setString(3, memOfSubstationID);
				preparedStmt.setString(4, baseVoltageId);
				preparedStmt.execute();
				BaseVoltage baseVoltage = BaseVoltage.searchBaseVoltage(
						baseVoltages, baseVoltageId);
				Substation subst = Substation.searchSubstation(
						substations, memOfSubstationID);
				VoltageLevel ab = new VoltageLevel(refId, refName, baseVoltage, subst);
				voltageLevels.add(ab);
				LoadXMLSQL.equipmentContainers.add(ab);
				LoadXMLSQL.powerSystemResources.add(ab);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("SQL Exception Error in loading voltage level objects from the CIM file", e);
			
		}
		return voltageLevels;
	}
	
	/**
	 * Search for voltage level object with a given rdfID.
	 *
	 * @param ab the ab
	 * @param rdfId the rdf id
	 * @return the voltage level
	 */
	static VoltageLevel searchVoltageLevel (ArrayList<VoltageLevel> ab, String rdfId) {
		VoltageLevel objectFound = null;
		for (VoltageLevel objIt : ab) {
			if (objIt.rdfID.equals(rdfId)) {
				objectFound = objIt;
				break;
			}
		}
		return objectFound;
	}
}
