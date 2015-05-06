package com.caeps.assign;

public class SynchronousMachine extends ConductingEquipment{

	public double ratedS;
	public GeneratingUnit memberOfGen;
	public RegulatingControl regControl;
	public EquipmentContainer memberOfEquipmentContainer;
	public BaseVoltage baseVoltage;
	
	
	public SynchronousMachine (String rdfId,String name, double ratedS, GeneratingUnit memberOfGen, RegulatingControl regControl, EquipmentContainer memberOfEquipmentContainer, BaseVoltage baseVoltage){
		super(rdfId,name);
		this.ratedS=ratedS;
		this.memberOfGen=memberOfGen;
		this.regControl=regControl;
		this.memberOfEquipmentContainer=memberOfEquipmentContainer;
		this.baseVoltage=baseVoltage;
		
	}

}
