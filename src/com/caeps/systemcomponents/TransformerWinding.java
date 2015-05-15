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
 * The Class TransformerWinding.
 */
public class TransformerWinding  extends ConductingEquipment{

	/** The r. */
	public double r;
	
	/** The x. */
	public double x;
	
	/** The power tfr. */
	public PowerTransformer powerTfr;
	
	/** The base voltage. */
	public BaseVoltage baseVoltage;
	
	/**
	 * Instantiates a new transformer winding.
	 *
	 * @param rdfId the rdf id
	 * @param name the name
	 * @param r the r
	 * @param x the x
	 * @param powerTfr the power tfr
	 * @param baseVoltage the base voltage
	 */
	public TransformerWinding(String rdfId, String name, double r, double x,
			PowerTransformer powerTfr, BaseVoltage baseVoltage){
		super(rdfId, name);
		rdfID = rdfId;
		this.r = r;
		this.x = x;
		this.powerTfr = powerTfr;
		this.baseVoltage = baseVoltage;
	}
	
	/**
	 * Gets the transformer winding.
	 *
	 * @param doc the doc
	 * @param conn the conn
	 * @param powertransformers the powertransformers
	 * @param baseVoltages the base voltages
	 * @return the transformer winding
	 */
	static ArrayList<TransformerWinding> getTransformerWinding(Document doc,
			Connection conn, ArrayList<PowerTransformer> powertransformers,
			ArrayList<BaseVoltage> baseVoltages){
		ArrayList<TransformerWinding> transformerWindings = new ArrayList<TransformerWinding>();
		String query = null;
		PreparedStatement preparedStmt;
		try {
			query = "DELETE FROM TransformerWinding";
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
			
			NodeList subList = doc.getElementsByTagName("cim:TransformerWinding");
			for (int i = 0; i < subList.getLength(); i++) {
				Node nd = subList.item(i);
				String refId = GetParam.getParam(nd, "rdf:ID");
				String name = GetParam.getParam(nd, "cim:IdentifiedObject.name");
				Double r = Double.parseDouble(GetParam.getParam(nd,
						"cim:TransformerWinding.r"));
				Double x = Double.parseDouble(GetParam.getParam(nd,
						"cim:TransformerWinding.x"));
				String memOfPowerTransformer = GetParam.getParam(nd,
						"cim:TransformerWinding.MemberOf_PowerTransformer")
						.substring(1);
				String baseVoltageId = GetParam.getParam(nd,
						"cim:ConductingEquipment.BaseVoltage").substring(1);
				query = "insert into TransformerWinding values (?,?,?,?,?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, refId);
				preparedStmt.setString(2, name);
				preparedStmt.setDouble(3, r);
				preparedStmt.setDouble(4, x);
				preparedStmt.setString(5, memOfPowerTransformer);
				preparedStmt.setString(6, baseVoltageId);
				preparedStmt.execute();
				PowerTransformer powerTrans = PowerTransformer.searchPowerTransformer(
						powertransformers, memOfPowerTransformer);
				BaseVoltage baseVoltage = BaseVoltage.searchBaseVoltage(baseVoltages,
						baseVoltageId);
				TransformerWinding ab = new TransformerWinding(refId, name,r, x, 
						powerTrans, baseVoltage);
				LoadXMLSQL.powerSystemResources.add(ab);
				LoadXMLSQL.conductingEquipments.add(ab);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transformerWindings;
	}
	
}
