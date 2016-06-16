package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import converter.Converter;
import converter.types.MarshallType;
import converter.types.UnmarshallType;
import database.DatabaseQuery;
import models.AssemblySession;
import models.RegulationsAndAmandments;
import models.rs.gov.parlament.amandmani.Amandman;
import models.rs.gov.parlament.amandmani.Amandmani;
import models.rs.gov.parlament.amandmani.StatusAmandmana;
import models.rs.gov.parlament.propisi.Propis;
import models.rs.gov.parlament.propisi.StatusAkta;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Session extends Controller {

    public static void time() {
        String userType = session.get("user-type");
        AssemblySession assemblySession = new AssemblySession();
    	render("Session/time.html", userType, assemblySession);
    }

	public static void setSchedule() {
        String userType = session.get("user-type");
        AssemblySession assemblySession = new AssemblySession();
    	render("Session/schedule.html", userType, assemblySession);
    }
    
    public static void schedule(String sessionDate, String sessionTime) {
    	String[] dates = sessionDate.split("-");
    	sessionDate=dates[2]+"."+dates[1]+"."+dates[0];    	
    	AssemblySession.scheduleSession(sessionTime, sessionDate);    	
    	time();
    }
    
    public static void show() {
        String userType = session.get("user-type");
        AssemblySession assemblySession = new AssemblySession();
    	
    	List<Object> regulationsToVote = DatabaseQuery.searchByStatus("PREDLOZEN", "REGULATIONS");
    	List<Object> amandmentsToVote = DatabaseQuery.searchByStatus("PREDLOZEN", "AMANDMENTS");
    	ArrayList<RegulationsAndAmandments> content = new ArrayList<RegulationsAndAmandments>();
    	
    	for (Object regulation : regulationsToVote) {
			ArrayList<Amandman> amandments = new ArrayList<>();
			for (Object amandment : amandmentsToVote)
				if (((Propis)regulation).getUriPropisa().equals(((Amandman)amandment).getDeoZaIzmenu().getUriPropisa()))
					amandments.add((Amandman) amandment);
			RegulationsAndAmandments collection = new RegulationsAndAmandments((Propis) regulation, amandments);
			content.add(collection);
		}
    	if (content.size() != 0)
    		render("Session/show.html", userType, assemblySession, content);
    	else {
        	AssemblySession.finishSession();
        	time();    		
    	}
    }
    
    public static void stop() {
		AssemblySession.finishSession();
		time();   
    }
    
    public static void regulation(String regulationId, String votingFirstResult, int votesFirstNumberYes, int votesFirstNumberNo, int votesFirstNumberOff) {
    	if (votingFirstResult.equals("no")) {
    		updateRegulation(false, regulationId, votesFirstNumberYes, votesFirstNumberNo, votesFirstNumberOff, true);
    		show();
    	}
    }
    
    public static void amandment(String amandmentId, String votingAmandmentResult, int votesAmandmentNumberYes, int votesAmandmentNumberNo, int votesAmandmentNumberOff) {
    	Date currentDate = new Date();
    	GregorianCalendar calendar = new GregorianCalendar();
    	calendar.setTime(currentDate);
    	XMLGregorianCalendar date = null;
		try {
			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		Amandman amandment = DatabaseQuery.readAmendmentFromDatabase(amandmentId);		
		amandment.getPreambula().setBrojGlasovaZa(votesAmandmentNumberYes);
		amandment.getPreambula().setBrojGlasovaProtiv(votesAmandmentNumberNo);
		amandment.getPreambula().setBrojGlasovaUzdrzano(votesAmandmentNumberOff);
		amandment.getPreambula().setDatumGlasanja(date);
		if (votingAmandmentResult.equals("yes"))
			amandment.getPreambula().setStatus(StatusAmandmana.PRIHVACEN);
		else
			amandment.getPreambula().setStatus(StatusAmandmana.ODBIJEN);
    }
    
    public static void finals(String regulationFinalId, String votingFinalResult, int votesFinalNumberYes, int votesFinalNumberNo, int votesFinalNumberOff) {
    	if (votingFinalResult.equals("no"))
    		updateRegulation(false, regulationFinalId, votesFinalNumberYes, votesFinalNumberNo, votesFinalNumberOff, false);
    	else
    		updateRegulation(true, regulationFinalId, votesFinalNumberYes, votesFinalNumberNo, votesFinalNumberOff, false);

    	// TODO OVDE IDE PRECISCAVANJE DOKUMENATA
    	/*
    	Propis regulationToClean = DatabaseQuery.readRegulationFromDatabase(regulationFinalId);
    	List<Amandman> amandments = DatabaseQuery.searchAmandmentsByRegulationId(regulationFinalId);
    	List<Amandman> amandmentsToClean = new ArrayList<>(); 
    	for (Amandman amandman : amandments) {
			if (amandman.getPreambula().getStatus() == StatusAmandmana.PREDLOZEN)
				amandmentsToClean.add(amandman);
		}
		regulationToClean, amandmentsToClean
    	*/    	
    	
    	show();
    }
    
    @Before(unless="time")
    public static void checkAccess() {
    	if (!session.get("user-type").equals("PREDSEDNIK"))
    		time();
    }
    
    private static void updateRegulation(boolean accepted, String id, int votesYes, int votesNo, int votesOff, boolean rejectAmandments) {
    	Date currentDate = new Date();
    	GregorianCalendar calendar = new GregorianCalendar();
    	calendar.setTime(currentDate);
    	XMLGregorianCalendar date = null;
		try {
			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		Propis regulation = DatabaseQuery.readRegulationFromDatabase(id);
		regulation.getPreambula().setBrojGlasovaZa(votesYes);
		regulation.getPreambula().setBrojGlasovaProtiv(votesNo);
		regulation.getPreambula().setBrojGlasovaUzdrzano(votesOff);
		regulation.getPreambula().setDatumGlasanja(date);
		if (!accepted)
			regulation.getPreambula().setStatus(StatusAkta.ODBIJEN);
		else
			regulation.getPreambula().setStatus(StatusAkta.PRIHVACEN);
		
		if (rejectAmandments) {
			List<Amandman> amandments = DatabaseQuery.searchAmandmentsByRegulationId(id);
			for (Amandman amandman : amandments) {
				amandman.getPreambula().setStatus(StatusAmandmana.ODBIJEN);
				String xml = Converter.marshall(MarshallType.TO_STRING, amandman, "");
				DatabaseQuery.writeXmlToDatabase(amandman.getUriAmandmana(), xml);
			}
		}
		
		String xmlFile = Converter.marshall(MarshallType.TO_STRING, regulation, "");
		DatabaseQuery.writeXmlToDatabase(id, xmlFile);
		
		show();
    }
}
