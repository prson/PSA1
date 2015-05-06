package com.caeps.assign;

public class Load extends ConductingEquipment{
	
	public double pfixed;
	public double qfixed;
	public EquipmentContainer memberOfEquipmentContainer;
	public BaseVoltage baseVoltage;
	
	public Load(String rdfId,String name, double pfixed, double qfixed, EquipmentContainer memberOfEquipmentContainer, BaseVoltage baseVoltage){
		super(rdfId,name);
		this.pfixed=pfixed;
		this.qfixed=qfixed;
		this.memberOfEquipmentContainer=memberOfEquipmentContainer;
		this.baseVoltage=baseVoltage;
	}
}
