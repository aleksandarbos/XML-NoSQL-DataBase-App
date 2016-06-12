package controllers;

import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Search extends Controller {

    public static void show() {
        String userType = session.get("user-type");
    	render(userType);
    }
    
    public static void search(String documentText, String documentDomain, String documentName, String documentStatus, String documentType, String user, String authority, String collection,
    						String nominatedDateFrom, String nominatedDateTo, String adoptionDateFrom, String adoptionDateTo, String announcementDateFrom, String announcementDateTo, 
    						String inuseDateFrom, String inuseDateTo, String withdrawalDateFrom, String withdrawalDateTo, 
    						int votesYesFrom, int votesYesTo, int votesNoFrom, int votesNoTo, int votesOffFrom, int votesOffTo) {
    	
    	show();
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
