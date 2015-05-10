package com.caeps.assign;

import java.util.ArrayList;

public class ConductingEquipment extends Equipment{

	public ConductingEquipment (String rdfId, String n){
		super(rdfId,n);
	}
	
	static ConductingEquipment searchConductingEquipment (ArrayList<ConductingEquipment> ab, String rdfId) {
		ConductingEquipment objectFound = null;
		for (ConductingEquipment objIt : ab) {
			if (objIt.rdfID.equals(rdfId)) {
				objectFound = objIt;
				break;
			}
		}
		return objectFound;
	}


}
