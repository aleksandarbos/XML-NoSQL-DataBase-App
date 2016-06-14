package controllers;

import dal.RegulationsDAO;
import models.rs.gov.parlament.propisi.Propis;
import play.mvc.Controller;
import play.mvc.With;

import javax.ws.rs.GET;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

@With(Secure.class)
public class Search extends Controller {

    public static void show() {
        String userType = session.get("user-type");
    	render(userType);
    }

    @GET
    public static void search(String documentText, String documentDomain, String documentName, String documentStatus, String documentType, String user, String authority, String collection,
    						String nominatedDateFrom, String nominatedDateTo, String adoptionDateFrom, String adoptionDateTo, String announcementDateFrom, String announcementDateTo, 
    						String inuseDateFrom, String inuseDateTo, String withdrawalDateFrom, String withdrawalDateTo, 
    						int votesYesFrom, int votesYesTo, int votesNoFrom, int votesNoTo, int votesOffFrom, int votesOffTo) throws IOException, JAXBException {
    	
    	textSearch(documentText);
    }

    private static void textSearch(String textQuery) throws IOException, JAXBException {
        HashMap<String, Propis> searchResults = RegulationsDAO.fetchRegulationsByQuery(textQuery);
        Collection<Propis> results = searchResults.values();
        renderTemplate("Search/show.html", results);
    }
    
    public static void topdf(int id) {
    	System.out.println("daj pdf za dokument: " + id);
    	show();
    }
    
    public static void preview(int id) {
    	System.out.println("daj html za dokument: " + id);
    	show();
    }
}
