package com.caeps.assign;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class LoadXMLSQL {
	static ArrayList<EquipmentContainer> equipmentContainers = new ArrayList<EquipmentContainer>();
	static ArrayList<PowerSystemResource> powerSystemResources = new ArrayList<PowerSystemResource>();
	static ArrayList<ConductingEquipment> conductingEquipments = new ArrayList<ConductingEquipment>();
	private static Logger logger = Logger.getLogger(LoadXMLSQL.class);

	public static void main(String args[]) {
		Connection conn = null;
		try {
			logger.debug("Entering the main function");
			String url = "jdbc:mysql://localhost:3306/";
			String username = "root";
			String password = "root";
			conn = (new ConnectToDB()).establishConnection(url, username,
					password);
			String filename = "opencim3sub.xml";
			(new CreateTable()).createTables(conn);
			Document doc = (new LoadDocument()).buildDocument(filename);
			ArrayList<BaseVoltage> baseVoltages = BaseVoltage.getBaseVoltages(
					doc, conn);
			ArrayList<Substation> substations = Substation.getSubstations(doc,
					conn);
			ArrayList<VoltageLevel> voltageLevels = VoltageLevel
					.getVoltageLevel(doc, conn, substations, baseVoltages);
			EquipmentContainer.updateEquipmentContainerDB(conn);
			ArrayList<GeneratingUnit> generatingUnits = GeneratingUnit
					.getGeneratingUnit(doc, conn, equipmentContainers);
			ArrayList<PowerTransformer> powerTransformers = PowerTransformer
					.getPowerTransformers(doc, conn, substations);
			ArrayList<RegulatingControl> regulatingControls = RegulatingControl
					.getRegulatingControl(doc, conn);
			ArrayList<SynchronousMachine> synchronousMachines = SynchronousMachine
					.getSynchronousMachine(doc, conn, equipmentContainers,
							baseVoltages, generatingUnits, regulatingControls);
			ArrayList<Load> loads = Load.getLoad(doc, conn,
					equipmentContainers, baseVoltages);
			ArrayList<TransformerWinding> transformerWindings = TransformerWinding
					.getTransformerWinding(doc, conn, powerTransformers,
							baseVoltages);
			ArrayList<Breaker> breakers = Breaker.getBreakers(doc, conn,
					equipmentContainers, baseVoltages);
			ArrayList<Disconnector> disconnectors = Disconnector
					.getDisconnectors(doc, conn, equipmentContainers,
							baseVoltages);
			PowerSystemResource.updatePowerSystemResourcesDB(conn);
			ArrayList<Analog> analogs = Analog.getAnalogs(doc, conn,
					powerSystemResources);

			ArrayList<ACLineSegment> lineSegments = ACLineSegment
					.getLineSegments(doc, conn, equipmentContainers,
							baseVoltages);
			ArrayList<ConnectivityNode> connectivityNodes = ConnectivityNode
					.getConnectivityNodes(doc, conn, voltageLevels);
			ArrayList<Terminal> terminals = Terminal.getTerminals(doc, conn,
					conductingEquipments, connectivityNodes);

		}

		finally {
			try {
				conn.close();
				logger.debug("Closing the database connection");
			} catch (SQLException e) {
				logger.error("Unable to close the database connection", e);
			}
		}
	}

}
