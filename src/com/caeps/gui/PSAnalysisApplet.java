package com.caeps.gui;

import java.sql.SQLException;

import javax.swing.JApplet;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.caeps.assign.LoadXMLSQL;

public class PSAnalysisApplet extends JApplet{
	
	private static Logger logger = Logger.getLogger(LoadXMLSQL.class);
	
	public void init(){
		PSAnalysisPanel psAnalysisPanel=new PSAnalysisPanel();
		setSize(800, 600);
		add(psAnalysisPanel);
		
	}
	
}
