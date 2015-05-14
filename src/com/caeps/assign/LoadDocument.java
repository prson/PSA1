package com.caeps.assign;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadDocument.
 */
public class LoadDocument {
	
	/**
	 * Builds the document.
	 *
	 * @param filename the filename
	 * @return the document
	 */
	Document buildDocument(String filename) {
		Document doc = null; 
		try {
			File file = new File(filename);
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = docBuilder.parse(file);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}
}
