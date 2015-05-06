package com.caeps.assign;

public class Analog extends ConductingEquipment{
	public double normalValue;
	public String measurementType;
	public PowerSystemResource memberOfPowerSystemResource;
	
	public Analog(String rdfId,String name, double normalValue, String measurementType, PowerSystemResource memberOfPowerSystemResource){
		super(rdfId,name);
		this.normalValue=normalValue;
		this.measurementType=measurementType;
		this.memberOfPowerSystemResource=memberOfPowerSystemResource;
	}

}
