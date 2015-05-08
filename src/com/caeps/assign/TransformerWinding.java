package com.caeps.assign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TransformerWinding  extends ConductingEquipment{

	public double r;
	public double x;
	public PowerTransformer powerTfr;
	public BaseVoltage baseVoltage;
	
	public TransformerWinding(String rdfId,String name, double r, double x, PowerTransformer powerTfr, BaseVoltage baseVoltage){
		super(rdfId,name);
		rdfID=rdfId;
		this.r=r;
		this.x=x;
		this.powerTfr=powerTfr;
		this.baseVoltage=baseVoltage;
	}
	
	static ArrayList<TransformerWinding> getTransformerWinding(Document doc, Connection conn, ArrayList<PowerTransformer> powertransformers, ArrayList<BaseVoltage> baseVoltages){
		ArrayList<TransformerWinding> transformerWindings=new ArrayList<TransformerWinding>();
		String query = null;
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute("delete from TransformerWinding");
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
				TransformerWinding ab = new TransformerWinding(refId, name,r, x, powerTrans, baseVoltage);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transformerWindings;
	}
	
}
