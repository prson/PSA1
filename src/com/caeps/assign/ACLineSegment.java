package com.caeps.assign;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ACLineSegment extends ConductingEquipment {

	public String rdfID;
	public double gch;
	public double bch;
	public double r;
	public double x;
	public double g0ch;
	public double b0ch;
	public double length;
	public double r0;
	public double x0;
	public EquipmentContainer memberOfEquipmentContainer;
	public BaseVoltage baseVoltage;
	static Logger logger = Logger.getLogger(LoadXMLSQL.class);

	public ACLineSegment(String rdfId, String name, String localName,
			double gch, double bch, double r, double x, double g0ch,
			double b0ch, double length, double r0, double x0,
			EquipmentContainer memberOfEquipmentContainer,
			BaseVoltage baseVoltage) {
		super(name, localName);
		rdfID = rdfId;
		this.gch = gch;
		this.bch = bch;
		this.r = r;
		this.x = x;
		this.g0ch = g0ch;
		this.b0ch = b0ch;
		this.length = length;
		this.r0 = r0;
		this.x0 = x0;
		this.memberOfEquipmentContainer = memberOfEquipmentContainer;
		this.baseVoltage = baseVoltage;
	}

	static ArrayList<ACLineSegment> getLineSegments(Document doc,
			Connection conn, ArrayList<EquipmentContainer> equipmentcontainers,
			ArrayList<BaseVoltage> baseVoltages) {
		
		ArrayList<ACLineSegment> linesegments = new ArrayList<ACLineSegment>();
		logger.debug("Loading AC Line Segments from the file");
		NodeList subList = doc.getElementsByTagName("cim:ACLineSegment");
		for (int i = 0; i < subList.getLength(); i++) {
			Node nd = subList.item(i);
			String refId = GetParam.getParam(nd, "rdf:ID");
			String name = GetParam.getParam(nd, "cim:IdentifiedObject.name");
			String localName = GetParam.getParam(nd,
					"cim:IdentifiedObject.localName");
			double gch = Double.parseDouble(GetParam.getParam(nd,
					"cim:ACLineSegment.gch"));
			double bch = Double.parseDouble(GetParam.getParam(nd,
					"cim:ACLineSegment.bch"));
			double r = Double.parseDouble(GetParam.getParam(nd,
					"cim:ACLineSegment.r"));
			double x = Double.parseDouble(GetParam.getParam(nd,
					"cim:ACLineSegment.x"));
			double g0ch = Double.parseDouble(GetParam.getParam(nd,
					"cim:ACLineSegment.g0ch"));
			double b0ch = Double.parseDouble(GetParam.getParam(nd,
					"cim:ACLineSegment.b0ch"));
			double r0 = Double.parseDouble(GetParam.getParam(nd,
					"cim:ACLineSegment.r0"));
			double x0 = Double.parseDouble(GetParam.getParam(nd,
					"cim:ACLineSegment.x0"));
			double length = Double.parseDouble(GetParam.getParam(nd,
					"cim:Conductor.length"));
			String baseVoltageId = GetParam.getParam(nd,
					"cim:ConductingEquipment.BaseVoltage").substring(1);
			String memOfEquipmentContainer = GetParam.getParam(nd,
					"cim:Equipment.MemberOf_EquipmentContainer").substring(1);
			EquipmentContainer equipmentContainer = EquipmentContainer
					.searchEquipmentContainer(equipmentcontainers,
							memOfEquipmentContainer);
			BaseVoltage baseVoltage = BaseVoltage.searchBaseVoltage(
					baseVoltages, baseVoltageId);
			ACLineSegment ab = new ACLineSegment(refId, name, localName, gch,
					bch, r, x, g0ch, b0ch, length, r0, x0, equipmentContainer,
					baseVoltage);
			linesegments.add(ab);
			LoadXMLSQL.conductingEquipments.add(ab);
		}
		logger.debug("Read the ac line segment contents from the XML file");
		return linesegments;
	}
}
