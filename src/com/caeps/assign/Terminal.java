package com.caeps.assign;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Terminal extends IdentifiedObject{
	public String rdfID;
	public ConnectivityNode connNode;
	public ConductingEquipment conductingEquipment;
	static Logger logger = Logger.getLogger(Terminal.class);
	
	public Terminal(String rdfId,String name, String localName, ConnectivityNode connNode, ConductingEquipment conductingEquipment){
		super(name,localName);
		rdfID=rdfId;
		this.connNode=connNode;
		this.conductingEquipment=conductingEquipment;
	}
	
	static ArrayList<Terminal> getTerminals(Document doc,
			Connection conn, ArrayList<ConductingEquipment> conductingEquipments,
			ArrayList<ConnectivityNode> connectivityNodes) {
		ArrayList<Terminal> terminals= new ArrayList<Terminal>();
		logger.debug("Loading the terminal from the XML File");
		NodeList subList = doc.getElementsByTagName("cim:Terminal");
		for (int i = 0; i < subList.getLength(); i++) {
			Node nd = subList.item(i);
			String refId = GetParam.getParam(nd, "rdf:ID");
			String name = GetParam.getParam(nd, "cim:IdentifiedObject.name");
			String localName = GetParam.getParam(nd,
					"cim:IdentifiedObject.localName");
			String conductingEquipId = GetParam.getParam(nd,
					"cim:Terminal.ConductingEquipment").substring(1);
			String connectivityNodeId = GetParam.getParam(nd,
					"cim:Terminal.ConnectivityNode").substring(1);
			// System.out.println(conductingEquipId);
			// System.out.println(conductingequipments.size());
			ConductingEquipment identObj = ConductingEquipment.searchConductingEquipment(
					conductingEquipments, conductingEquipId);
			ConnectivityNode connNode = ConnectivityNode.searchConnectivityNode(
					connectivityNodes, connectivityNodeId);
			Terminal ab = new Terminal(refId, name, localName, connNode,
					identObj);
			terminals.add(ab);
		}
		logger.debug("Succesfully read the terminal contents from the XML File");
		return terminals;
	}

}
