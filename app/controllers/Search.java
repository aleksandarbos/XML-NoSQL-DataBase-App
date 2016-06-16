package controllers;

import dal.AmendmentsDAO;
import dal.RegulationsDAO;
import database.DatabaseQuery;
import play.mvc.Controller;
import play.mvc.With;

import java.util.Collection;
import java.util.HashMap;

@With(Secure.class)
public class Search extends Controller {

    public static void show() {
        String userType = session.get("user-type");
    	render(userType);
    }

    public static void searchText(String documentText, String documentType) {
		HashMap<String, Object> searchResults = null;
		flash("documentText", documentText);
		flash("documentType", documentType);

		if(documentType.equals("regulations")) {
			searchResults = RegulationsDAO.fetchRegulationsByQuery(documentText);
		} else {
			searchResults = AmendmentsDAO.fetchAmendmentsByQuery(documentText);
		}

		Collection<Object> results = searchResults.values();

        renderTemplate("Search/show.html", results);
    }

    public static void searchDetails(String documentName, String documentStatus, String documentType, String user, String authority, String collection,
    						String nominatedDateFrom, String nominatedDateTo, String adoptionDateFrom, String adoptionDateTo, String announcementDateFrom, String announcementDateTo, 
    						String inuseDateFrom, String inuseDateTo, String withdrawalDateFrom, String withdrawalDateTo, 
    						int votesYesFrom, int votesYesTo, int votesNoFrom, int votesNoTo, int votesOffFrom, int votesOffTo) {

        HashMap<String, Object> searchResults = DatabaseQuery.metadataSearch(documentName, documentStatus, documentType, user, authority, collection,
                nominatedDateFrom, nominatedDateTo, adoptionDateFrom, adoptionDateTo, announcementDateFrom, announcementDateTo,
                inuseDateFrom, inuseDateTo, withdrawalDateFrom, withdrawalDateTo, votesYesFrom, votesYesTo, votesNoFrom, votesNoTo, votesOffFrom, votesOffTo);

        

		flash("documentName", documentName);
		flash("documentStatus", documentStatus);
		flash("documentType", documentType);
		flash("user", user);
		flash("authority", authority);
		flash("collection", collection);
		flash("nominatedDateFrom", nominatedDateFrom);
		flash("nominatedDateTo", nominatedDateTo);
		flash("adoptionDateFrom", adoptionDateFrom);
		flash("adoptionDateTo", adoptionDateTo);
		flash("announcementDateFrom", announcementDateFrom);
		flash("announcementDateTo", announcementDateTo);
		flash("inuseDateFrom", inuseDateFrom);
		flash("inuseDateTo", inuseDateTo);
		flash("withdrawalDateFrom", withdrawalDateFrom);
		flash("withdrawalDateTo", withdrawalDateTo);
		flash("votesYesFrom", votesYesFrom);
		flash("votesYesTo", votesYesTo);
		flash("votesNoFrom", votesNoFrom);
		flash("votesNoTo", votesNoTo);
		flash("votesOffFrom", votesOffFrom);
		flash("votesOffTo", votesOffTo);
        
        

        
        Collection<Object> results = searchResults.values();
        renderTemplate("Search/show.html", results);
    }
}
