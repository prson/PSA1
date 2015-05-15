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
 * The Class Substation.
 */
public class Substation extends EquipmentContainer {

	/** The region_rdf id. */
	public String region_rdfID;

	/** The logger. */
	static Logger logger = Logger.getLogger(Substation.class);
	
	/**
	 * Instantiates a new substation.
	 *
	 * @param rdfId the rdf id
	 * @param n the n
	 * @param region_rdfId the region_rdf id
	 */
	public Substation(String rdfId, String n, String region_rdfId){
		super(rdfId, n);
		region_rdfID = region_rdfId;
	}
	
	/**
	 * Gets all substation components from the CIM file and returns them in an 
	 * array list and stores them in the database.
	 *
	 * @param doc the document
	 * @param conn the connection
	 * @return the substations
	 */
	static ArrayList<Substation> getSubstations(Document doc, Connection conn){
		ArrayList<Substation> substations=new ArrayList<Substation>();
		String query = null;
		PreparedStatement preparedStmt;
		NodeList subList;
		try {
			query = "DELETE FROM Substation";
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
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
				LoadXMLSQL.equipmentContainers.add(ab);
				LoadXMLSQL.powerSystemResources.add(ab);
			}
		} catch (SQLException e) {
			logger.error("SQL Exception Error in loading substation details into the database.", e);
			PSAnalysisPanel.consoleArea.append("\nSQL Exception Error in loading substation details into the database. Check logs for more details.");
		}
		return substations;
	}
	
	/**
	 * Search for substation component with given rdfID.
	 *
	 * @param substationsList the substations list
	 * @param rdfId the rdf id
	 * @return the substation
	 */
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
