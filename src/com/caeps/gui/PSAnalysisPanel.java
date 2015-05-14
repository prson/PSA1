package com.caeps.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.caeps.assign.ConnectToDB;
import com.caeps.assign.LoadDocument;
import com.caeps.assign.LoadXMLSQL;
import com.caeps.assign.TwoDArray;

public class PSAnalysisPanel extends JPanel {
	
	private static Logger logger = Logger.getLogger(LoadXMLSQL.class);
	JTextField connectionUsernameField;
	JTextField connectionUrlField;
	JPasswordField connectionPasswordField;
	JTextArea resultsArea;
	JTextField filenameUrlField;
	public static Connection conn;
	boolean connectionEstablished=false;
	Document doc;
	JTextArea consoleArea;
	
	public PSAnalysisPanel() {
		// TODO Auto-generated constructor stub
		
		JLabel connectionUrlLabel=new JLabel("Connection URL: ");
		
		connectionUrlField=new JTextField();
		connectionUrlField.setText("jdbc:mysql://localhost:3306/");
		connectionUrlField.setColumns(15);
		
		
		JLabel connectionUsernameLabel=new JLabel("Username: ");
		
		connectionUsernameField=new JTextField();
		connectionUsernameField.setText("root");
		connectionUsernameField.setColumns(5);
		
		JLabel connectionPasswordLabel=new JLabel("Password: ");
		
		connectionPasswordField=new JPasswordField();
		connectionPasswordField.setText("root");
		connectionPasswordField.setColumns(5);
		
		JButton establishConnectionButton=new JButton("Establish Connection");
		EstablishConnectionMouseListener establishListener=new EstablishConnectionMouseListener();
		establishConnectionButton.addMouseListener(establishListener);
		
		JPanel establishConnectionPanel=new JPanel();
		establishConnectionPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Enter the database connection parameters"),BorderFactory.createEmptyBorder(15,15,15,15)));
		establishConnectionPanel.add(connectionUrlLabel);
		establishConnectionPanel.add(connectionUrlField);
		establishConnectionPanel.add(connectionUsernameLabel);
		establishConnectionPanel.add(connectionUsernameField);
		establishConnectionPanel.add(connectionPasswordLabel);
		establishConnectionPanel.add(connectionPasswordField);
		establishConnectionPanel.add(establishConnectionButton);
		
		JLabel filenameUrlLabel=new JLabel("File Location: ");
		
		filenameUrlField=new JTextField();
		filenameUrlField.setText("opencim3sub.xml");
		filenameUrlField.setColumns(15);
		
		JButton loadFileButton=new JButton("Load File");
		LoadFileMouseListener loadFileMouseListener=new LoadFileMouseListener();
		loadFileButton.addMouseListener(loadFileMouseListener);
		
		JPanel loadFilePanel=new JPanel();
		loadFilePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Enter the XML file details"),BorderFactory.createEmptyBorder(15,15,15,15)));
		loadFilePanel.add(filenameUrlLabel);
		loadFilePanel.add(filenameUrlField);
		loadFilePanel.add(loadFileButton);
		
		JButton calcYBusButton=new JButton("Calc Y BUS");
		calcYBusButton.setPreferredSize(new Dimension(120,30));
		CalcYBusMouseListener calcYBusMouseListener=new CalcYBusMouseListener();
		calcYBusButton.addMouseListener(calcYBusMouseListener);
		
		JButton getSubstationsButton=new JButton("Get Substations");
		getSubstationsButton.setPreferredSize(new Dimension(120,30));
		JButton getLoadsButton=new JButton("Get Loads");
		getLoadsButton.setPreferredSize(new Dimension(120,30));
		JButton getGensButton=new JButton("Get Generators");
		getGensButton.setPreferredSize(new Dimension(120,30));
		
		JPanel buttonPanel=new JPanel();
//		buttonPanel.setPreferredSize(new Dimension(super.getWidth()*1/4,20));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setPreferredSize(new Dimension(200, 200));
		buttonPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Perform Ops"),BorderFactory.createEmptyBorder(5,5,5,5)));
		buttonPanel.add(calcYBusButton);
		buttonPanel.add(getSubstationsButton);
		buttonPanel.add(getLoadsButton);
		buttonPanel.add(getGensButton);
		
		resultsArea=new JTextArea();
		resultsArea.setPreferredSize(new Dimension(400, 200));
		resultsArea.setLineWrap(true);
		
		JPanel resultsPanel=new JPanel();
//		resultsPanel.setPreferredSize(new Dimension(super.getWidth()*1/2, 100));
		resultsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Results"),BorderFactory.createEmptyBorder(5,5,5,5)));
		resultsPanel.add(resultsArea);
		
		JPanel performOpsPanel=new JPanel();
		performOpsPanel.add(buttonPanel);
		performOpsPanel.add(resultsPanel);
		
		consoleArea=new JTextArea();
		consoleArea.setPreferredSize(new Dimension(780, 120));
		consoleArea.setForeground(Color.GREEN);
		consoleArea.setBackground(Color.BLACK);
		consoleArea.setText("Welcome!");
		
		JPanel consolePanel=new JPanel();
		consolePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Console"),BorderFactory.createEmptyBorder(5,5,5,5)));
		consolePanel.add(consoleArea);
		
		JButton exitButton=new JButton("Exit");
		ExitMouseListener exitMouseListener=new ExitMouseListener();
		exitButton.addMouseListener(exitMouseListener);
		
		exitButton.setPreferredSize(new Dimension(120,30));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(establishConnectionPanel);
		add(loadFilePanel);
		add(performOpsPanel);
		add(consolePanel);
		add(exitButton);
		
	}
	
	class EstablishConnectionMouseListener implements MouseListener{
		
		public void mouseClicked(MouseEvent e) {
			conn=null;
			doc=null;
			logger.debug("Inside establosh connection listener");
			conn = (new ConnectToDB()).establishConnection(connectionUrlField.getText(), connectionUsernameField.getText(),
					String.valueOf(connectionPasswordField.getPassword()));
			if(conn!=null){
				consoleArea.setForeground(Color.GREEN);
				consoleArea.setText("Congrats! Connection established.");
//			connectionEstablished=true;
			}
			else{
				consoleArea.setForeground(Color.RED);
				consoleArea.setText("Sorry! Connection not established, check the logs and try again.");
			}
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
		}
		public void mousePressed(MouseEvent e) {
		}
	}
	
	class LoadFileMouseListener implements MouseListener{
		
		public void mouseClicked(MouseEvent e) {
			logger.debug("Inside load file listener");
			doc = (new LoadDocument()).buildDocument(filenameUrlField.getText());
			
			if(doc!=null)
			{
				if(conn!=null){
					consoleArea.setForeground(Color.GREEN);
					consoleArea.setText("Congrats! File Read. Loading it to the database.....");
					LoadXMLSQL.readFile(doc, conn);
					
				}
				else{
					consoleArea.setForeground(Color.RED);
					consoleArea.setText("Ooopsss! File exists. But connection is not established. Please establish a connection and try again. Visit the logs for details");
					doc=null;
				}
			}
			else{
				consoleArea.setForeground(Color.RED);
				consoleArea.setText("Sorry! File could not be read, check the logs and try again.");
			}
			
			consoleArea.setForeground(Color.GREEN);
			consoleArea.setText("Congrats! File Read. Loaded in the database. Go ahead and perform operations!");
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
		}
		public void mousePressed(MouseEvent e) {
		}
	}
	
	class CalcYBusMouseListener implements MouseListener{
		
		public void mouseClicked(MouseEvent e) {
			logger.debug("Inside calculate ybus listener");
			if(conn==null){
				consoleArea.setForeground(Color.RED);
				consoleArea.setText("The connection is not established. Please establish connection and load the file before calculating the ybus!");
			}
			else if(doc==null)
			{
				consoleArea.setForeground(Color.RED);
				consoleArea.setText("The document has not been read yet. Please load the file before calculating the ybus!");
			}
			else{
				consoleArea.setForeground(Color.GREEN);
				consoleArea.setText("Calculating Y bus...");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				TwoDArray ybus=LoadXMLSQL.ybus;
				int numOfBuses=ybus.height;
				logger.debug("YBUS Matrix");
				String result="Y BUS=\n";
				for(int i = 0; i < numOfBuses; i++){
					for(int j = 0; j < numOfBuses; j++){
						String sign1=(ybus.values[i][j].real>0)?("+"):("");
						String sign2=(ybus.values[i][j].imaginary>0)?("+"):("");
						result=result+sign1+String.format("%.4f",ybus.values[i][j].real)+sign2+String.format("%.4f",ybus.values[i][j].imaginary)+"i   ";
					}
					result=result+"\n";
				}
				consoleArea.setText("Y Bus calculated");
				resultsArea.setText(result);
			}
				
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
		}
		public void mousePressed(MouseEvent e) {
		}
	}
	
	class ExitMouseListener implements MouseListener{
		
		public void mouseClicked(MouseEvent e) {
			logger.debug("Closing the connection and exiting!");
			if(conn!=null){
				try {
					logger.debug("Closing the connection and exiting!");
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
				System.exit(0);
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
		}
		public void mousePressed(MouseEvent e) {
		}
	}
	
	class GetSubstMouseListener implements MouseListener{
		
		public void mouseClicked(MouseEvent e) {
			logger.debug("Closing the connection and exiting!");
			if(conn!=null){
				try {
					logger.debug("Closing the connection and exiting!");
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
				System.exit(0);
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
		}
		public void mousePressed(MouseEvent e) {
		}
	}

}
