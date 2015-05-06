package com.caeps.assign;

public class VoltageLevel extends EquipmentContainer {

	public BaseVoltage baseVoltage;
	public Substation memberOfSubstation;

	public VoltageLevel(String rdfId, String n, BaseVoltage baseVoltage, Substation memberOfSubstation){
		super(rdfId,n);
		this.baseVoltage = baseVoltage;
		this.memberOfSubstation = memberOfSubstation;
	}
}
