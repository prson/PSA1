package com.caeps.systemcomponents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class EquipmentContainer.
 */
public class EquipmentContainer extends ConnectivityNodeContainer {
	
	/**
	 * Instantiates a new equipment container.
	 *
	 * @param rdfId the rdf id
	 * @param n the n
	 */
	public EquipmentContainer(String rdfId, String n){
		super(rdfId, n);
	}

	/**
	 * Search equipment container object from the list of equipment containers having a particular rdf id.
	 *
	 * @param ab the equipment container array list 
	 * @param rdfId the rdf id to be searched
	 * @return the equipment container 
	 */
	static EquipmentContainer searchEquipmentContainer(ArrayList<EquipmentContainer> ab,
			String rdfId) {
		EquipmentContainer objectFound = null;
		for (EquipmentContainer objIt : ab) {
			// System.out.println(objIt.getRdfID());
			// System.out.println(rdfId);
			if (objIt.getRdfID().equals(rdfId)) {
				// System.out.println(rdfId);
				objectFound = objIt;
				break;
			}
		}
		return objectFound;
	}
	
	/**
	 * enters all the equiment containers in the database
	 *
	 * @param conn the conn conection to teh database object
	 */
	static void updateEquipmentContainerDB(Connection conn){
		String query = "INSERT INTO equipmentcontainer SELECT * FROM (SELECT rdfId, name FROM voltagelevel UNION select rdfId, NAME FROM substation) as b; ";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
