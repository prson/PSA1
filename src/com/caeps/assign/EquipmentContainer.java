package com.caeps.assign;

import java.util.ArrayList;

public class EquipmentContainer extends ConnectivityNodeContainer {
	
	public EquipmentContainer(String rdfId, String n){
		super(rdfId,n);
	}

	static EquipmentContainer searchEquipmentContainer(ArrayList<EquipmentContainer> ab, String rdfId) {
		EquipmentContainer objectFound = null;
		for (EquipmentContainer objIt : ab) {
			// System.out.println(objIt.getRdfID());
			// System.out.println(rdfId);
			if (objIt.getRdfID().equals(rdfId)) {
				// System.out.println(rdfId);
				objectFound = objIt;
				break;
			}
		}
		return objectFound;
	}
}
