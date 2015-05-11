package com.caeps.assign;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConnectivityNode extends IdentifiedObject{
	public String rdfID;
	public VoltageLevel nodeContainerVoltLevel;
	static Logger logger = Logger.getLogger(ConnectivityNode.class);
	
	public ConnectivityNode(String rdfId,String name, String localName, VoltageLevel nodeContainer){
		super(name,localName);
		rdfID=rdfId;
		this.nodeContainerVoltLevel=nodeContainer;
	}

	static ArrayList<ConnectivityNode> getConnectivityNodes(Document doc,
			Connection conn, ArrayList<VoltageLevel> voltageLevels) {
		ArrayList<ConnectivityNode> connectivityNodes= new ArrayList<ConnectivityNode>();
		logger.debug("Reading the connectivity contents from the XML File");
		NodeList subList = doc.getElementsByTagName("cim:ConnectivityNode");
		for (int i = 0; i < subList.getLength(); i++) {
			Node nd = subList.item(i);
			String refId = GetParam.getParam(nd, "rdf:ID");
			String name = GetParam.getParam(nd, "cim:IdentifiedObject.name");
			String localName = GetParam.getParam(nd,
					"cim:IdentifiedObject.localName");
			String voltageLevelId = GetParam.getParam(nd,
					"cim:ConnectivityNode.ConnectivityNodeContainer")
					.substring(1);
			VoltageLevel voltLevel = VoltageLevel.searchVoltageLevel(voltageLevels,
					voltageLevelId);
			ConnectivityNode ab = new ConnectivityNode(refId, name,
					localName, voltLevel);
			connectivityNodes.add(ab);
		}
		logger.debug("Succesfully read the connectivity contents from the XML File");
		return connectivityNodes;
	}
	
	static ConnectivityNode searchConnectivityNode (ArrayList<ConnectivityNode> ab, String rdfId) {
		ConnectivityNode objectFound = null;
		for (ConnectivityNode objIt : ab) {
			if (objIt.rdfID.equals(rdfId)) {
				objectFound = objIt;
				break;
			}
		}
		return objectFound;
	}

}
