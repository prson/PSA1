package com.caeps.assign;

public class RegulatingControl extends ConductingEquipment{

	public double targetValue;
	
	public RegulatingControl(String rdfId,String name, double targetValue){
		super(rdfId,name);
		this.targetValue=targetValue;
	}
}
