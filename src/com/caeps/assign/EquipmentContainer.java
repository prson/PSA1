package com.caeps.assign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class EquipmentContainer extends ConnectivityNodeContainer {
	
	public EquipmentContainer(String rdfId, String n){
		super(rdfId,n);
	}

	static EquipmentContainer searchEquipmentContainer(ArrayList<EquipmentContainer> ab, String rdfId) {
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
