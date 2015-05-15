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
 * The Class RegulatingControl.
 */
public class RegulatingControl extends ConductingEquipment{

	/** The target value. */
	public double targetValue;
	
	/** The logger. */
	static Logger logger = Logger.getLogger(RegulatingControl.class);
	
	/**
	 * Instantiates a new regulating control.
	 *
	 * @param rdfId the rdf id
	 * @param name the name
	 * @param targetValue the target value
	 */
	public RegulatingControl(String rdfId,String name, double targetValue){
		super(rdfId, name);
		this.targetValue = targetValue;
	}
	
	/**
	 * Gets all regulating control components from the CIM file and returns them in an 
	 * array list and stores them in the database.
	 *
	 * @param doc the document
	 * @param conn the connection
	 * @return the regulating control
	 */
	static ArrayList<RegulatingControl> getRegulatingControl(Document doc, Connection conn){
		ArrayList<RegulatingControl> regulatingControls=new ArrayList<RegulatingControl>();
		String query = null;
		PreparedStatement preparedStmt;
		try {
			query = "DELETE FROM regulatingControl";
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
			NodeList subList = doc.getElementsByTagName("cim:RegulatingControl");
			for (int i = 0; i < subList.getLength(); i++) {
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd, "rdf:ID");
				String name = GetParam.getParam(nd, "cim:IdentifiedObject.name");
				Double targetValue = Double.parseDouble(GetParam.getParam(nd,
						"cim:RegulatingControl.targetValue"));
				query = "insert into regulatingControl values (?,?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, name);
				preparedStmt.setDouble(3, targetValue);
				preparedStmt.execute();
				RegulatingControl ab = new RegulatingControl(refId, name, targetValue);
				regulatingControls.add(ab);
				LoadXMLSQL.powerSystemResources.add(ab);
				LoadXMLSQL.conductingEquipments.add(ab);
			}
		} catch (SQLException e) {
			logger.error("SQL Exception Error in loading regulating control details into the database.", e);
			PSAnalysisPanel.consoleArea.append("\nSQL Exception Error in loading regulating control details into the database. Check logs for more details.");
		}
		return regulatingControls;
	}
	
	/**
	 * Search for regulating control component with given rdfId.
	 *
	 * @param ab the ab
	 * @param rdfId the rdf id
	 * @return the regulating control
	 */
	static RegulatingControl searchRegulatingControl(ArrayList<RegulatingControl> ab,
			String rdfId) {
		RegulatingControl objectFound = null;
		for (RegulatingControl objIt : ab) {
			if (objIt.getRdfID().equals(rdfId)) {
				objectFound = objIt;
				break;
			}
		}
		return objectFound;
	}
}
