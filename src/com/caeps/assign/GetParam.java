package com.caeps.assign;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class GetParam {
	
	public static String getParam(Node node, String param) {
		Element nodeElement = (Element) node;
		String paramValue = "";
		if (nodeElement.hasAttribute(param)) {
			paramValue = nodeElement.getAttribute(param);
		} else {
			Element attrNode = (Element) nodeElement
					.getElementsByTagName(param).item(0);
			paramValue = attrNode.getTextContent();
			if (paramValue == "") {
				paramValue = getParam(attrNode, "rdf:resource");
			}
		}
		return paramValue;
	}
}
