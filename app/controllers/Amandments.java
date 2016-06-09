package controllers;

import play.mvc.Controller;

public class Amandments extends Controller {

    public static void show() {
    	render();
    }

    public static void create(String amandmentName, int affectedRegulation, int affectedClause, String affectedType, String user, String amandmentContent) {
    	System.out.println("dodaj akt: " + amandmentName + ", predlozio korisnik: " + user + ", sadrzaj dokumenta: "+amandmentContent+", menja se dokument sa id: "+affectedRegulation+", id clana: "+affectedRegulation+", tip izmene "+affectedType);
    	show();
    }
}
