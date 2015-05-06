package com.caeps.assign;


public class PowerTransformer extends ConductingEquipment{
	public Substation memberOfSubstation;
	
	public PowerTransformer(String rdfId,String name, Substation memberOfSubstation){
		super(rdfId,name);
		this.memberOfSubstation=memberOfSubstation;
	}

}
