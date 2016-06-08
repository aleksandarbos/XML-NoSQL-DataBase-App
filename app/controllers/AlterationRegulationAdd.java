package controllers;

import play.data.validation.Required;
import play.mvc.Controller;

public class AlterationRegulationAdd extends Controller {

    public static void show(){
    	render();
    }
    
    public static void create(String regulationName, String user, String regulationPath) {
    	System.out.println("dodaj akt: " + regulationName + ", predlozio korisnik: " + user + ", putanja do dokumenta: "+regulationPath);
    	
    	show();
    }
}
