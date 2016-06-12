package controllers;

import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Overview extends Controller {

    public static void show(){
    	render();
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
