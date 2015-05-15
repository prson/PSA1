package com.caeps.systemcomponents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.caeps.gui.PSAnalysisPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class PowerSystemResource.
 */
public class PowerSystemResource extends IdentifiedObject {
	
	/** The logger. */
	static Logger logger = Logger.getLogger(PowerSystemResource.class);
	
	/**
	 * Instantiates a new power system resource.
	 *
	 * @param rdfId the rdf id
	 * @param n the name
	 */
	public PowerSystemResource(String rdfId, String n){
		super(rdfId, n);
	}

	/**
	 * Search power system resource.
	 *
	 * @param ab the ab
	 * @param rdfId the rdf id
	 * @return the power system resource
	 */
	static PowerSystemResource searchPowerSystemResource(ArrayList<PowerSystemResource> ab, String rdfId) {
		PowerSystemResource objectFound = null;
		for (PowerSystemResource objIt : ab) {
			if (objIt.rdfID.equals(rdfId)) {
				objectFound = objIt;
				break;
			}
		}
		return objectFound;
	}
	
	/**
	 * Update power system resources db.
	 *
	 * @param conn the connection
	 */
	static void updatePowerSystemResourcesDB(Connection conn){
		String query = "INSERT INTO powersystemresource SELECT * FROM (SELECT rdfId, name FROM equipmentcontainer UNION select rdfId, NAME FROM generatingunit UNION select rdfId, NAME FROM breaker UNION select rdfId, NAME FROM disconnector UNION select rdfId, NAME FROM loads UNION select rdfId, NAME FROM powertransformer UNION select rdfId, NAME FROM regulatingcontrol UNION select rdfId, NAME FROM synchronousmachine UNION select rdfId, NAME FROM transformerwinding ) as b";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
		} catch (SQLException e) {
			logger.error("SQL Exception Error in loading power system resource details into the database.", e);
			PSAnalysisPanel.consoleArea.append("\nSQL Exception Error in loading power system resource details into the database. Check logs for more details.");
		}
		
	}
}
