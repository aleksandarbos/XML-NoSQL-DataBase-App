package controllers;

import database.DatabaseAccessor;
import models.rs.gov.parlament.korisnici.Gradjani;
import models.rs.gov.parlament.korisnici.GradjaninTip;
import models.rs.gov.parlament.korisnici.Korisnici;
import play.mvc.Controller;
import util.Converter;
import util.MarshallType;
import util.UnmarshallType;

import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * Created by aleksandar on 3.6.16..
 */
public class Register extends Controller{

    private static DatabaseAccessor db = DatabaseAccessor.getInstance();

    public static void addNewUser(GradjaninTip citizen) throws JAXBException {
        System.out.println("[INFO] Fetching users form database...");

        DatabaseAccessor db = DatabaseAccessor.getInstance();
        String usersStr = DatabaseAccessor.readXmlFromDatabase("/parlament/01/korisnici.xml");
        Korisnici users = (Korisnici) Converter.unmarshall(UnmarshallType.FROM_STRING, usersStr, Korisnici.class);
        List<Gradjani> citizens = users.getGradjani();
        Gradjani citizensList = citizens.get(0);
        citizensList.getGradjanin().add(citizen);

        String usersXmlString = Converter.marshall(MarshallType.TO_STRING, users, "");

        DatabaseAccessor.writeXmlToDatabase("/parlament/01/korisnici.xml", usersXmlString);

        renderHtml("Added to database: Name: "+ citizen.getIme() + ", "
                + "Surname: " + citizen.getPrezime() + ", Email: " + citizen.getEmail()
                + "<br/><a>Verify the content at: http://147.91.177.194:8000/v1/documents?database=Tim16&uri=/parlament/01/korisnici.xml</a>");

    }

}
