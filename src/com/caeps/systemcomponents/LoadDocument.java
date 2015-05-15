package com.caeps.systemcomponents;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadDocument.
 */
public class LoadDocument {

	private static Logger logger = Logger.getLogger(LoadDocument.class);

	/**
	 * Builds the document.
	 *
	 * @param filename the filename
	 * @return the document
	 */
	public Document buildDocument(String filename) {
		Document doc = null; 
		try {
			File file = new File(filename);
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = docBuilder.parse(file);
			logger.debug("File succesfully read.");
		} catch (SAXException e) {
			logger.error("SAX Exception while reading file", e);
		} catch (IOException e) {
			logger.error("IO Exception while reading file", e);
		} catch (ParserConfigurationException e) {
			logger.error("Parser Configuration Exception while reading file", e);
		}
		return doc;
	}
}
