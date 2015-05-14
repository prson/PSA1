package com.caeps.assign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class PowerTransformer.
 */
public class PowerTransformer extends ConductingEquipment{
	
	/** The member of substation. */
	public Substation memberOfSubstation;
	
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
	 * Gets the power transformers.
	 *
	 * @param doc the doc
	 * @param conn the conn
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return powertransformers;
	}

	/**
	 * Search power transformer.
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
