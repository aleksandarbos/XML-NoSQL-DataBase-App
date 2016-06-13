package controllers;

import converter.XMLTransformation;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Overview extends Controller {

    public static void show(){
        String userType = session.get("user-type");
    	render(userType);
    }
    
    public static void topdf(int id) {
    	System.out.println("daj pdf za dokument: " + id);
    	XMLTransformation.transformToPdf();
    	show();
    }
    
    public static void preview(int id) {
    	System.out.println("daj html za dokument: " + id);
    	XMLTransformation.transformToXhtml();
    	show();
    }
}
