package com.caeps.assign;


public class TransformerWinding  extends ConductingEquipment{

	public double r;
	public double x;
	public PowerTransformer powerTfr;
	public BaseVoltage baseVoltage;
	
	public TransformerWinding(String rdfId,String name, double r, double x, PowerTransformer powerTfr, BaseVoltage baseVoltage){
		super(rdfId,name);
		rdfID=rdfId;
		this.r=r;
		this.x=x;
		this.powerTfr=powerTfr;
		this.baseVoltage=baseVoltage;
	}
	
}
