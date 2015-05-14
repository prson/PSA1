package com.caeps.assign;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectivityNode.
 */
public class ConnectivityNode extends IdentifiedObject{
	
	/** The rdf id. */
	public String rdfID;
	
	/** The node container volt level. */
	public VoltageLevel nodeContainerVoltLevel;
	
	/** The logger. */
	static Logger logger = Logger.getLogger(ConnectivityNode.class);
	
	/**
	 * Instantiates a new connectivity node.
	 *
	 * @param rdfId the rdf id
	 * @param name the name
	 * @param localName the local name
	 * @param nodeContainer the node container
	 */
	public ConnectivityNode(String rdfId,String name, String localName, 
			VoltageLevel nodeContainer){
		super(name, localName);
		rdfID = rdfId;
		this.nodeContainerVoltLevel = nodeContainer;
	}

	/**
	 * Gets the connectivity nodes.
	 *
	 * @param doc the doc
	 * @param conn the conn
	 * @param voltageLevels the voltage levels
	 * @return the connectivity nodes
	 */
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
	
	/**
	 * Search connectivity node.
	 *
	 * @param ab the ab
	 * @param rdfId the rdf id
	 * @return the connectivity node
	 */
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
