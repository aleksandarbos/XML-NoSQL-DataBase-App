package controllers;

import models.rs.gov.parlament.korisnici.GradjaninTip;
import play.mvc.Controller;

/**
 * Created by aleksandar on 3.6.16..
 */
public class Register extends Controller{

    public static void addNewUser(GradjaninTip citizen) {
        renderText("Ime: "+ citizen.getIme() + ", "
                + "Prezime: " + citizen.getPrezime() + ", Email: " + citizen.getEmail());
    }

}
