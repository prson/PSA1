package com.caeps.assign;

public class Substation extends EquipmentContainer {

	public String region_rdfID;

	public Substation(String rdfId, String n, String region_rdfId){
		super(rdfId,n);
		region_rdfID = region_rdfId;
	}
}
