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
			ArrayList<Line> lines = Line.getLines(doc, conn);
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
			
			calculateYBus(substations, lineSegments, terminals);

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
	
	static void calculateYBus(ArrayList<Substation> substations,
			ArrayList<ACLineSegment> lineSegments, ArrayList<Terminal> terminals) {
		// TODO Auto-generated method stub
		
		int numOfBuses=substations.size();
		TwoDArray ybus=new TwoDArray(numOfBuses, numOfBuses);
		for(ACLineSegment objLS:lineSegments){
			int count=0;
			Substation subs1 = null, subs2;
			for(Terminal objTerm:terminals){
				if(objTerm.conductingEquipment!=null){
//				logger.debug(objLS.rdfID);
//				logger.debug(objTerm.name);
//				logger.debug(objTerm.conductingEquipment.name);
				if(objTerm.conductingEquipment.getRdfID().equals(objLS.getRdfID())){
					count++;
					if(count==2){
						subs2=objTerm.connNode.nodeContainerVoltLevel.memberOfSubstation;
						int a=substations.indexOf(subs1);
						int b=substations.indexOf(subs2);
						logger.debug("Substations indexes for the"+objLS.r+" i"+objLS.x+" are: "+a+" and "+b);
						ComplexNumber zline=new ComplexNumber(objLS.r,objLS.x);
						ComplexNumber yline=ComplexNumber.cDiv(new ComplexNumber(1,0), zline);
						ybus.values[a][b]=ComplexNumber.cDif(ybus.values[a][b], yline);
						ybus.values[b][a]=ComplexNumber.cDif(ybus.values[b][a], yline);
						ybus.values[a][a]=ComplexNumber.cSum(ybus.values[a][a], yline);
						ybus.values[b][b]=ComplexNumber.cSum(ybus.values[b][b], yline);
						break;
					}
					else{
						subs1=objTerm.connNode.nodeContainerVoltLevel.memberOfSubstation;
					}
				}
				}
			}
		}
		logger.debug("YBUS Matrix");
		System.out.println("***************************************************");
		for(int i=0; i<numOfBuses;i++){
			for(int j=0; j<numOfBuses;j++){
				System.out.print(ybus.values[i][j].real+" i"+ybus.values[i][j].imaginary+"   ");
			}System.out.println();
		}
		System.out.println("***************************************************");
		
		
	}
		
}