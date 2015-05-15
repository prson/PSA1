package com.caeps.systemcomponents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class Line.
 */
public class Line extends EquipmentContainer {
	
	/** The logger. */
	static Logger logger = Logger.getLogger(EquipmentContainer.class);
	
	
	/**
	 * Instantiates a new line.
	 *
	 * @param rdfId the rdf id
	 * @param name the name
	 */
	public Line(String rdfId,String name){
		super(rdfId, name);
	}
	
	/**
	 * Gets the lines.
	 *
	 * @param doc the doc
	 * @param conn the conn
	 * @return the lines
	 */
	static ArrayList<Line> getLines(Document doc, Connection conn){
		ArrayList<Line> lines=new ArrayList<Line>();
		NodeList subList;
		logger.debug("Reading lines content from the XML");
		subList = doc.getElementsByTagName("cim:Line");
		for (int i = 0; i < subList.getLength(); i++) {
			Node nd = subList.item(i);
			String refId = GetParam.getParam(nd, "rdf:ID");
			String name = GetParam.getParam(nd, "cim:IdentifiedObject.name");
			Line ab = new Line(refId, name);
			lines.add(ab);
			LoadXMLSQL.equipmentContainers.add(ab);
		}
		logger.debug("Succesfully read lines content from the XML");
		return lines;
	}

}
