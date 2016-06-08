package controllers;

import play.mvc.Controller;

public class AlterationOverview extends Controller {

    public static void show() {
    	render();
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
}
