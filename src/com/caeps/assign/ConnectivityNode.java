package com.caeps.assign;

import java.sql.Connection;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConnectivityNode extends IdentifiedObject{
	public String rdfID;
	public VoltageLevel nodeContainerVoltLevel;
	
	public ConnectivityNode(String rdfId,String name, String localName, VoltageLevel nodeContainer){
		super(name,localName);
		rdfID=rdfId;
		this.nodeContainerVoltLevel=nodeContainer;
	}

	static ArrayList<ConnectivityNode> getConnectivityNodes(Document doc,
			Connection conn, ArrayList<VoltageLevel> voltageLevels) {
		ArrayList<ConnectivityNode> connectivityNodes= new ArrayList<ConnectivityNode>();
		System.out.println("Connectivity Nodes::");
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
