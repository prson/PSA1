package com.caeps.systemcomponents;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class ConductingEquipment.
 */
public class ConductingEquipment extends Equipment{

	/**
	 * Instantiates a new conducting equipment.
	 *
	 * @param rdfId the rdf id
	 * @param n the name
	 */
	public ConductingEquipment (String rdfId, String n){
		super(rdfId,n);
	}
	
	/**
	 * Search for conducting equipment component with given rdfId.
	 *
	 * @param ab the ab
	 * @param rdfId the rdf id
	 * @return the conducting equipment
	 */
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
