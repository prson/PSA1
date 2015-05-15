package com.caeps.systemcomponents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseVoltage.
 */
public class BaseVoltage extends IdentifiedObject{

	/** The nominal value. */
	public double nominalValue;

	/**
	 * Instantiates a new base voltage.
	 *
	 * @param rdfId the rdf id
	 * @param n the n
	 * @param nomVal the nom val
	 */
	public BaseVoltage(String rdfId, String n, double nomVal){
		super(rdfId, n);
		nominalValue = nomVal;
	}
	
	/**
	 * Gets the base voltages.
	 *
	 * @param doc the doc
	 * @param conn the conn
	 * @return the base voltages
	 */
	static ArrayList<BaseVoltage> getBaseVoltages(Document doc, Connection conn){
		ArrayList<BaseVoltage> baseVoltages = new ArrayList<BaseVoltage>();
		String query = null;
		PreparedStatement preparedStmt = null;
		try {
//			query="DELETE FROM BaseVoltage";
//			preparedStmt=conn.prepareStatement(query);
//			preparedStmt.execute();
			NodeList subList = doc.getElementsByTagName("cim:BaseVoltage");
			for (int i = 0; i < subList.getLength(); i++) {
				query = "INSERT INTO BaseVoltage VALUES (?,?,?)";
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd, "rdf:ID");
				String refName = GetParam.getParam(nd, "cim:IdentifiedObject.name");
				double nominalVoltage = Double.parseDouble(GetParam.getParam(nd,
						"cim:BaseVoltage.nominalVoltage"));
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, refName);
				preparedStmt.setDouble(3, nominalVoltage);
				preparedStmt.execute();
				BaseVoltage baseVoltageObj = new BaseVoltage(refId, refName, nominalVoltage);
				baseVoltages.add(baseVoltageObj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baseVoltages;
	}
	
	/**
	 * Search base voltage.
	 *
	 * @param ab the ab
	 * @param rdfId the rdf id
	 * @return the base voltage
	 */
	static BaseVoltage searchBaseVoltage(ArrayList<BaseVoltage> ab, String rdfId) {
		BaseVoltage objectFound = null;
		for (BaseVoltage objIt : ab) {
			if (objIt.rdfID.equals(rdfId)) {
				objectFound = objIt;
				break;
			}
		}
		return objectFound;
	}
}