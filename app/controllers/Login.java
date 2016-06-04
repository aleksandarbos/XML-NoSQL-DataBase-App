package controllers;

import database.DatabaseAccessor;
import models.rs.gov.parlament.korisnici.GradjaninTip;
import play.mvc.Controller;

/**
 * Created by aleksandar on 3.6.16..
 */
public class Login extends Controller {

    private static DatabaseAccessor db = DatabaseAccessor.getInstance();

    public static void login(GradjaninTip citizen) {
        renderText("Recieved data: " + citizen.getIme() + ", " + citizen.getPrezime() + ", " + citizen.getEmail());
    }
}
