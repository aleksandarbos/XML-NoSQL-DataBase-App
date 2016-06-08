package controllers;

import play.mvc.Controller;

public class AlterationAmendmentAdd extends Controller {

    public static void show() {
    	render();
    }

    public static void create(String amandmentName, int affectedRegulation, int affectedClause, String affectedType, String user, String amandmentPath) {
    	System.out.println("dodaj akt: " + amandmentName + ", predlozio korisnik: " + user + ", putanja do dokumenta: "+amandmentPath+", menja se dokument sa id: "+affectedRegulation+", id clana: "+affectedRegulation+", tip izmene "+affectedType);
    	show();
    }
}
