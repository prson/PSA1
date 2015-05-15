package com.caeps.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.caeps.systemcomponents.ACLineSegment;
import com.caeps.systemcomponents.ConnectToDB;
import com.caeps.systemcomponents.Load;
import com.caeps.systemcomponents.LoadDocument;
import com.caeps.systemcomponents.LoadXMLSQL;
import com.caeps.systemcomponents.Substation;
import com.caeps.systemcomponents.SynchronousMachine;
import com.caeps.systemcomponents.TwoDArray;

public class PSAnalysisPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoadXMLSQL.class);
	JTextField connectionUsernameField;
	JTextField connectionUrlField;
	JPasswordField connectionPasswordField;
	JTextArea resultsArea;
	JTextField filenameUrlField;
	public static Connection conn;
	boolean connectionEstablished = false;
	Document doc;
	public static JTextArea consoleArea;

	public PSAnalysisPanel() {
		JLabel connectionUrlLabel = new JLabel("Connection URL: ");

		connectionUrlField = new JTextField();
		connectionUrlField.setText("jdbc:mysql://localhost:3306/");
		connectionUrlField.setColumns(15);

		JLabel connectionUsernameLabel = new JLabel("Username: ");

		connectionUsernameField = new JTextField();
		connectionUsernameField.setText("root");
		connectionUsernameField.setColumns(5);

		JLabel connectionPasswordLabel = new JLabel("Password: ");

		connectionPasswordField = new JPasswordField();
		connectionPasswordField.setText("root");
		connectionPasswordField.setColumns(5);

		JButton establishConnectionButton = new JButton("Establish Connection");
		EstablishConnectionMouseListener establishListener = new EstablishConnectionMouseListener();
		establishConnectionButton.addMouseListener(establishListener);

		JPanel establishConnectionPanel = new JPanel();
		establishConnectionPanel
				.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory
								.createTitledBorder("Enter the database connection parameters"),
						BorderFactory.createEmptyBorder(15, 15, 15, 15)));
		establishConnectionPanel.add(connectionUrlLabel);
		establishConnectionPanel.add(connectionUrlField);
		establishConnectionPanel.add(connectionUsernameLabel);
		establishConnectionPanel.add(connectionUsernameField);
		establishConnectionPanel.add(connectionPasswordLabel);
		establishConnectionPanel.add(connectionPasswordField);
		establishConnectionPanel.add(establishConnectionButton);

		JLabel filenameUrlLabel = new JLabel("File Location: ");

		filenameUrlField = new JTextField();
		filenameUrlField.setText("opencim3sub.xml");
		filenameUrlField.setColumns(15);

		JButton loadFileButton = new JButton("Load File");
		LoadFileMouseListener loadFileMouseListener = new LoadFileMouseListener();
		loadFileButton.addMouseListener(loadFileMouseListener);

		JPanel loadFilePanel = new JPanel();
		loadFilePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Enter the XML file details"),
				BorderFactory.createEmptyBorder(15, 15, 15, 15)));
		loadFilePanel.add(filenameUrlLabel);
		loadFilePanel.add(filenameUrlField);
		loadFilePanel.add(loadFileButton);

		JButton calcYBusButton = new JButton("Calc Y BUS");
		CalcYBusMouseListener calcYBusMouseListener = new CalcYBusMouseListener();
		calcYBusButton.addMouseListener(calcYBusMouseListener);

		JButton getSubstationsButton = new JButton("Get Substations");
		GetSubstMouseListener getSubstMouseListener = new GetSubstMouseListener();
		getSubstationsButton.addMouseListener(getSubstMouseListener);

		JButton getLoadsButton = new JButton("Get Loads");
		GetLoadsMouseListener getLoadsMouseListener = new GetLoadsMouseListener();
		getLoadsButton.addMouseListener(getLoadsMouseListener);

		JButton getGensButton = new JButton("Get Generators");
		GetGensMouseListener getGensMouseListener = new GetGensMouseListener();
		getGensButton.addMouseListener(getGensMouseListener);

		JButton getLinesButton = new JButton("Get Lines");
		GetLinesMouseListener getLinesMouseListener = new GetLinesMouseListener();
		getLinesButton.addMouseListener(getLinesMouseListener);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setPreferredSize(new Dimension(200, 200));
		buttonPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Perform Ops"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		buttonPanel.add(calcYBusButton);
		buttonPanel.add(getSubstationsButton);
		buttonPanel.add(getLoadsButton);
		buttonPanel.add(getGensButton);
		buttonPanel.add(getLinesButton);

		resultsArea = new JTextArea();
		resultsArea.setPreferredSize(new Dimension(500, 200));
		resultsArea.setLineWrap(true);
		resultsArea.setEditable(false);

		JPanel resultsPanel = new JPanel();
		resultsPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Results"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		resultsPanel.add(resultsArea);

		JPanel performOpsPanel = new JPanel();
		performOpsPanel.add(buttonPanel);
		performOpsPanel.add(resultsPanel);

		consoleArea = new JTextArea();
		consoleArea.setForeground(Color.WHITE);
		consoleArea.setBackground(Color.BLACK);
		consoleArea.setEditable(false);
		consoleArea.append("Welcome!");

		DefaultCaret caret = (DefaultCaret) consoleArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scroll = new JScrollPane(consoleArea);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(800, 100));

		JPanel consolePanel = new JPanel();
		consolePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Console"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		consolePanel.add(scroll);

		JButton exitButton = new JButton("Exit");
		ExitMouseListener exitMouseListener = new ExitMouseListener();
		exitButton.addMouseListener(exitMouseListener);

		exitButton.setPreferredSize(new Dimension(120, 30));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(establishConnectionPanel);
		add(loadFilePanel);
		add(performOpsPanel);
		add(consolePanel);
		add(exitButton);

	}

	class EstablishConnectionMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					logger.error("Error in closing previous connection.", e1);
					consoleArea
							.append("\nError in closing previous connection. Check logs for details.");
				}
			}
			conn = null;
			doc = null;
			logger.debug("Inside establish connection listener");
			conn = (new ConnectToDB()).establishConnection(
					connectionUrlField.getText(),
					connectionUsernameField.getText(),
					String.valueOf(connectionPasswordField.getPassword()));
			if (conn != null) {
				consoleArea.append("\nCongrats! Connection established.");
			} else {
				consoleArea
						.append("\nSorry! Connection not established, check the logs and try again.");
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

	class LoadFileMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			logger.debug("Inside load file listener");
			doc = (new LoadDocument())
					.buildDocument(filenameUrlField.getText());

			if (doc != null) {
				if (conn != null) {
					consoleArea
							.append("\nCongrats! File read. Loading it to the database.....");
					LoadXMLSQL.readFile(doc, conn);
					consoleArea
							.append("\nCongrats! File read. Loaded to the database. Go ahead and perform operations!");
				} else {
					consoleArea
							.append("\nOoopsss! File exists. But connection is not established. Please establish a connection and try again. Visit the logs for details");
					logger.error("Ooopsss! File exists. But connection is not established. Please establish a connection and try again. Visit the logs for details");
					doc = null;
				}
			} else {
				consoleArea
						.append("\nSorry! File could not be read, check the logs and try again.");
				logger.error("Sorry! File could not be read, check the logs and try again.");
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

	class CalcYBusMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			logger.debug("Inside calculate ybus listener");
			if (conn == null) {
				consoleArea
						.append("\nThe connection is not established. Please establish connection and load the file before calculating the ybus!");
				logger.error("The connection is not established. Please establish connection and load the file before calculating the ybus!");
			} else if (doc == null) {
				consoleArea
						.append("\nThe document has not been read yet. Please load the file before calculating the ybus!");
				logger.error("The document has not been read yet. Please load the file before calculating the ybus!");
			} else {
				consoleArea.append("\nCalculating Y bus...");
				TwoDArray ybus = LoadXMLSQL.ybus;
				int numOfBuses = ybus.height;
				logger.debug("Y Bus Matrix");
				String result = "Y Bus Matrix (No. of Buses/Substations = "
						+ numOfBuses + ")\n\n";
				for (int i = 0; i < numOfBuses; i++) {
					for (int j = 0; j < numOfBuses; j++) {
						String sign1 = (ybus.values[i][j].real > 0) ? ("+")
								: ("");
						String sign2 = (ybus.values[i][j].imaginary > 0) ? ("+")
								: ("");
						result = result
								+ sign1
								+ String.format("%.4f", ybus.values[i][j].real)
								+ sign2
								+ String.format("%.4f",
										ybus.values[i][j].imaginary) + "i   ";
					}
					result = result + "\n";
				}
				consoleArea.append("\nY Bus calculated");
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

	class ExitMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			logger.debug("Closing the connection and exiting!");
			if (conn != null) {
				try {
					logger.debug("Closing the connection and exiting!");
					conn.close();
				} catch (SQLException e1) {
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

	class GetSubstMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			logger.debug("Inside get substation listener");
			if (conn == null) {
				consoleArea
						.append("\nThe connection is not established. Please establish connection and load the file before getting the substations!");
			} else if (doc == null) {
				consoleArea
						.append("\nThe document has not been read yet. Please load the file before getting the substations!");
			} else {
				consoleArea.append("\nGetting substations...");
				ArrayList<Substation> substations = LoadXMLSQL.substations;
				int numOfSubstations = substations.size();
				logger.debug("Substations");
				String result = "Substations \n\nNo.    Name\n";
				for (int i = 0; i < numOfSubstations; i++) {
					result = result + (i + 1) + "        "
							+ substations.get(i).name + "\n";
				}
				consoleArea.append("\nSubstations retrieved");
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

	class GetLoadsMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			logger.debug("Inside get loads listener");
			if (conn == null) {
				consoleArea
						.append("\nThe connection is not established. Please establish connection and load the file before getting the loads!");
			} else if (doc == null) {
				consoleArea
						.append("\nThe document has not been read yet. Please load the file before getting the loads!");
			} else {
				consoleArea.append("\nGetting loads...");
				ArrayList<Load> loads = LoadXMLSQL.loads;
				int numOfLoads = loads.size();
				logger.debug("Getting Loads");
				String result = "Loads \n\nNo.    Name                 Substation/Voltage          PFixed      QFixed\n";
				for (int i = 0; i < numOfLoads; i++) {
					result = result + (i + 1) + "        " + loads.get(i).name
							+ "        "
							+ loads.get(i).memberOfEquipmentContainer.name
							+ "        " + loads.get(i).pfixed + "        "
							+ loads.get(i).qfixed + "\n";
				}
				consoleArea.append("\nLoads retrieved");
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

	class GetGensMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			logger.debug("Inside get generators listener");
			if (conn == null) {
				consoleArea
						.append("\nThe connection is not established. Please establish connection and load the file before getting the generators!");
			} else if (doc == null) {
				consoleArea
						.append("\nThe document has not been read yet. Please load the file before getting the generators!");
			} else {
				consoleArea.append("\nGetting generators...");
				ArrayList<SynchronousMachine> gens = LoadXMLSQL.synchronousMachines;
				int numOfGens = gens.size();
				logger.debug("Getting Generators");
				String result = "Generators \n\nNo.    Name                 RatedS        Substation/Voltage   \n";
				for (int i = 0; i < numOfGens; i++) {
					result = result + (i + 1) + "        " + gens.get(i).name
							+ "        " + gens.get(i).ratedS + "        "
							+ gens.get(i).memberOfEquipmentContainer.name
							+ "        " + "\n";
				}
				consoleArea.append("\nGenerators retrieved");
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

	class GetLinesMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			logger.debug("Inside get lines listener");
			if (conn == null) {
				consoleArea
						.append("\nThe connection is not established. Please establish connection and load the file before getting the lines!");
			} else if (doc == null) {
				consoleArea
						.append("\nThe document has not been read yet. Please load the file before getting the lines!");
			} else {
				consoleArea.append("\nGetting lines...");
				ArrayList<ACLineSegment> lines = LoadXMLSQL.lineSegments;
				int numOfLines = lines.size();
				logger.debug("Getting Lines");
				String result = "Lines \n\nNo.    Name          R               X                 SubstationFrom        SubstationTo   \n";
				for (int i = 0; i < numOfLines; i++) {
					result = result + (i + 1) + "        " + lines.get(i).name
							+ "        " + lines.get(i).r + "        "
							+ lines.get(i).x + "        "
							+ lines.get(i).substationFrom.name
							+ "               "
							+ lines.get(i).substationTo.name + "\n";
				}
				consoleArea.append("\nLines retrieved");
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
}
