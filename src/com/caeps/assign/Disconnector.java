package com.caeps.assign;



public class Disconnector extends ConductingEquipment {
	public EquipmentContainer memberOfEquipmentContainer;
	public BaseVoltage baseVoltage;
	public String state;
	
	public Disconnector(String rdfId,String name, String state, BaseVoltage baseVoltage, EquipmentContainer memberOfEquipmentContainer){
		super(rdfId,name);
		this.state=state;
		this.baseVoltage=baseVoltage;
		this.memberOfEquipmentContainer=memberOfEquipmentContainer;
	}

}
