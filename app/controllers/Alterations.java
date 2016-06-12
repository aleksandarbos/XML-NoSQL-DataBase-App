package controllers;

import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Alterations extends Controller {

    public static void show() {
        String userType = session.get("user-type");
    	render(userType);
    }
    
    public static void delete(int id) {
    	System.out.println("obrisi dokument: " + id);
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
    
    @Before(unless="time")
    public static void checkAccess() {
    	if (session.get("user-type").equals("GRADJANIN"))
    		Overview.show();
    }
}
