package com.caeps.assign;

import java.util.ArrayList;


public class PowerSystemResource extends IdentifiedObject {
	
	public PowerSystemResource(String rdfId, String n){
		super(rdfId,n);
	}

	static PowerSystemResource searchPowerSystemResource(ArrayList<PowerSystemResource> ab, String rdfId) {
		PowerSystemResource objectFound = null;
		for (PowerSystemResource objIt : ab) {
			if (objIt.rdfID.equals(rdfId)) {
				objectFound = objIt;
				break;
			}
		}
		return objectFound;
	}
}
