package com.caeps.assign;

public class GeneratingUnit extends ConductingEquipment{

	public double maxP;
	public double minP;
	public EquipmentContainer memberOfEquipmentContainer;

	public GeneratingUnit(String rdfId, String n, double maxPow, double minPow, EquipmentContainer memberOfEquipmentContainer){
		super(rdfId,n);
		maxP = maxPow;
		minP = minPow;
		this.memberOfEquipmentContainer = memberOfEquipmentContainer;
	}
	
}
