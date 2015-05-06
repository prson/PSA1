package com.caeps.assign;


public class Breaker extends ConductingEquipment {
	public EquipmentContainer memberOfEquipmentContainer;
	public BaseVoltage baseVoltage;
	public String state;
	
	public Breaker(String rdfId,String name, String state, BaseVoltage baseVoltage, EquipmentContainer memberOfEquipmentContainer){
		super(rdfId,name);
		this.state=state;
		this.baseVoltage=baseVoltage;
		this.memberOfEquipmentContainer=memberOfEquipmentContainer;
	}

}
