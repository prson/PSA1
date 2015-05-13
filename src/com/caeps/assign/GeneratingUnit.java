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
 * The Class GeneratingUnit.
 */
public class GeneratingUnit extends ConductingEquipment{

	/** The max p. */
	public double maxP;
	
	/** The min p. */
	public double minP;
	
	/** The member of equipment container. */
	public EquipmentContainer memberOfEquipmentContainer;

	/**
	 * Instantiates a new generating unit.
	 *
	 * @param rdfId the rdf id
	 * @param n the n
	 * @param maxPow the max pow
	 * @param minPow the min pow
	 * @param memberOfEquipmentContainer the member of equipment container
	 */
	public GeneratingUnit(String rdfId, String n, double maxPow, double minPow, EquipmentContainer memberOfEquipmentContainer){
		super(rdfId,n);
		maxP = maxPow;
		minP = minPow;
		this.memberOfEquipmentContainer = memberOfEquipmentContainer;
	}
	
	/**
	 * Gets the generating unit.
	 *
	 * @param doc the doc
	 * @param conn the conn
	 * @param equipmentcontainers the equipmentcontainers
	 * @return the generating unit
	 */
	static ArrayList<GeneratingUnit> getGeneratingUnit(Document doc, Connection conn, ArrayList<EquipmentContainer> equipmentcontainers){
		ArrayList<GeneratingUnit> generatingUnits=new ArrayList<GeneratingUnit>();
		String query = null;
		PreparedStatement preparedStmt;
		try {
			query="DELETE FROM GeneratingUnit";
			preparedStmt=conn.prepareStatement(query);
			preparedStmt.execute();
			NodeList subList = doc.getElementsByTagName("cim:ThermalGeneratingUnit");
			for (int i = 0; i < subList.getLength(); i++) {
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd, "rdf:ID");
				String name = GetParam.getParam(nd, "cim:IdentifiedObject.name");
				String memOfEquipmentContainer = GetParam.getParam(nd,"cim:Equipment.MemberOf_EquipmentContainer").substring(1);
				Double maxOperatingP = Double.parseDouble(GetParam.getParam(nd,"cim:GeneratingUnit.maxOperatingP"));
				Double minOperatingP = Double.parseDouble(GetParam.getParam(nd,"cim:GeneratingUnit.minOperatingP"));
				query = "insert into GeneratingUnit values (?,?,?,?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, name);
				preparedStmt.setDouble(3, maxOperatingP);
				preparedStmt.setDouble(4, minOperatingP);
				preparedStmt.setString(5, memOfEquipmentContainer);
				preparedStmt.execute();
				EquipmentContainer subst = EquipmentContainer.searchEquipmentContainer(equipmentcontainers, memOfEquipmentContainer);
				// System.out.println(baseVoltage.localName+subst.localName);
				GeneratingUnit generatingUnitObj = new GeneratingUnit(refId,name, maxOperatingP, minOperatingP, subst);
				generatingUnits.add(generatingUnitObj);
				LoadXMLSQL.powerSystemResources.add(generatingUnitObj);
				LoadXMLSQL.conductingEquipments.add(generatingUnitObj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return generatingUnits;
	}
	
	/**
	 * Search generating unit.
	 *
	 * @param ab the ab
	 * @param rdfId the rdf id
	 * @return the generating unit
	 */
	static GeneratingUnit searchGeneratingUnit(ArrayList<GeneratingUnit> ab, String rdfId) {
		GeneratingUnit objectFound = null;
		for (GeneratingUnit objIt : ab) {
			if (objIt.getRdfID().equals(rdfId)) {
				objectFound = objIt;
				break;
			}
		}
		return objectFound;
	}
}
