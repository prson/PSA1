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
 * The Class PowerTransformer.
 */
public class PowerTransformer extends ConductingEquipment{
	
	/** The member of substation. */
	public Substation memberOfSubstation;
	
	/** The logger. */
	static Logger logger = Logger.getLogger(PowerTransformer.class);
	
	/**
	 * Instantiates a new power transformer.
	 *
	 * @param rdfId the rdf id
	 * @param name the name
	 * @param memberOfSubstation the member of substation
	 */
	public PowerTransformer(String rdfId,String name, Substation memberOfSubstation){
		super(rdfId, name);
		this.memberOfSubstation = memberOfSubstation;
	}
	
	/**
	 * Gets all power transformer components from the CIM file and returns them in an 
	 * array list and stores them in the database.
	 *
	 * @param doc the document
	 * @param conn the connection
	 * @param substations the substations
	 * @return the power transformers
	 */
	static ArrayList<PowerTransformer> getPowerTransformers(Document doc, 
			Connection conn, ArrayList<Substation> substations){
		ArrayList<PowerTransformer> powertransformers = new ArrayList<PowerTransformer>();
		String query = null;
		PreparedStatement preparedStmt;
		NodeList subList;
		try {
			query = "DELETE FROM PowerTransformer";
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
			subList = doc.getElementsByTagName("cim:PowerTransformer");
			for (int i = 0; i < subList.getLength(); i++) {
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd, "rdf:ID");
				String name = GetParam.getParam(nd, "cim:IdentifiedObject.name");
				String memOfSubstationId = GetParam.getParam(nd,
						"cim:Equipment.MemberOf_EquipmentContainer").substring(1);
				query = "insert into powerTransformer values (?,?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, memOfSubstationId);
				preparedStmt.execute();
				Substation substation = Substation.searchSubstation(substations,
						memOfSubstationId);
				PowerTransformer ab = new PowerTransformer(refId, name,substation);
				powertransformers.add(ab);
				LoadXMLSQL.powerSystemResources.add(ab);
				LoadXMLSQL.conductingEquipments.add(ab);
			}
		} catch (SQLException e) {
			logger.error("SQL Exception Error in loading power transformer details into the database.", e);
			PSAnalysisPanel.consoleArea.append("\nSQL Exception Error in loading power transformer details into the database. Check logs for more details.");
		}
		return powertransformers;
	}

	/**
	 * Search for power transformer component with given rdfId.
	 *
	 * @param ab the ab
	 * @param rdfId the rdf id
	 * @return the power transformer
	 */
	static PowerTransformer searchPowerTransformer(ArrayList<PowerTransformer> ab,
			String rdfId) {
		PowerTransformer objectFound = null;
		for (PowerTransformer objIt : ab) {
			if (objIt.getRdfID().equals(rdfId)) {
				objectFound = objIt;
				break;
			}
		}
		return objectFound;
	}

}
