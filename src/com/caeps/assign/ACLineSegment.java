package com.caeps.assign;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class ACLineSegment.
 */
public class ACLineSegment extends ConductingEquipment {

	/** The local name. */
	public String localName;
	
	/** The gch. */
	public double gch;
	
	/** The bch. */
	public double bch;
	
	/** The r. */
	public double r;
	
	/** The x. */
	public double x;
	
	/** The g0ch. */
	public double g0ch;
	
	/** The b0ch. */
	public double b0ch;
	
	/** The length. */
	public double length;
	
	/** The r0. */
	public double r0;
	
	/** The x0. */
	public double x0;
	
	/** The member of equipment container. */
	public EquipmentContainer memberOfEquipmentContainer;
	
	/** The base voltage. */
	public BaseVoltage baseVoltage;
	
	/** The logger. */
	static Logger logger = Logger.getLogger(LoadXMLSQL.class);

	/**
	 * Instantiates a new AC line segment.
	 *
	 * @param rdfId the rdf id
	 * @param name the name
	 * @param localName the local name
	 * @param gch the gch
	 * @param bch the bch
	 * @param r the r
	 * @param x the x
	 * @param g0ch the g0ch
	 * @param b0ch the b0ch
	 * @param length the length
	 * @param r0 the r0
	 * @param x0 the x0
	 * @param memberOfEquipmentContainer the member of equipment container
	 * @param baseVoltage the base voltage
	 */
	public ACLineSegment(String rdfId, String name, String localName,
			double gch, double bch, double r, double x, double g0ch,
			double b0ch, double length, double r0, double x0,
			EquipmentContainer memberOfEquipmentContainer,
			BaseVoltage baseVoltage) {
		super(rdfId, name);
		this.localName=localName;
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

	/**
	 * Gets the line segments.
	 *
	 * @param doc the doc
	 * @param conn the conn
	 * @param equipmentcontainers the equipmentcontainers
	 * @param baseVoltages the base voltages
	 * @return the line segments
	 */
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
