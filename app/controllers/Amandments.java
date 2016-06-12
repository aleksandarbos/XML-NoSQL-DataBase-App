package controllers;

import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Amandments extends Controller {

    public static void show() {
        String userType = session.get("user-type");
    	render(userType);
    }

    public static void create(String amandmentName, int affectedRegulation, int affectedClause, String affectedType, String user, String amandmentContent) {
    	System.out.println("dodaj akt: " + amandmentName + ", predlozio korisnik: " + user + ", sadrzaj dokumenta: "+amandmentContent+", menja se dokument sa id: "+affectedRegulation+", id clana: "+affectedRegulation+", tip izmene "+affectedType);
    	show();
    }
    
    @Before(unless="time")
    public static void checkAccess() {
    	if (session.get("user-type").equals("GRADJANIN"))
    		Overview.show();
    }
}
