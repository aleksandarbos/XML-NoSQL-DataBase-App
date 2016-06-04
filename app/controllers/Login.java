package controllers;

import com.marklogic.client.io.InputStreamHandle;
import database.DatabaseAccessor;
import models.rs.gov.parlament.korisnici.*;
import play.mvc.Controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by aleksandar on 3.6.16..
 */
public class Login extends Controller {

    private static DatabaseAccessor db = DatabaseAccessor.getInstance();

    public static void login(GradjaninTip citizen) throws IOException {

        renderText("Recieved data: " + citizen.getIme() + ", " + citizen.getPrezime() + ", " + citizen.getEmail());

    }

}
