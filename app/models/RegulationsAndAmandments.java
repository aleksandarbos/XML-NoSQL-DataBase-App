package models;

import java.util.ArrayList;
import java.util.List;

import models.rs.gov.parlament.amandmani.Amandman;
import models.rs.gov.parlament.propisi.Propis;

public class RegulationsAndAmandments {
	
	private Propis regulation;
	private List<Amandman> amandments;	
	
	public RegulationsAndAmandments(Propis regulation, ArrayList<Amandman> amandments) {
		this.regulation = regulation;
		this.amandments = amandments;
	}
	
	public Propis getRegulation() {
		return regulation;
	}
	
	public List<Amandman> getAmandments() {
		return amandments;
	} 
}
