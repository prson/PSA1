package com.caeps.assign;

public class BaseVoltage extends IdentifiedObject{

	public double nominalValue;

	public BaseVoltage(String rdfId, String n, double nomVal){
		super(rdfId,n);
		nominalValue = nomVal;
	}

}
