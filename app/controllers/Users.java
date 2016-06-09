package controllers;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.xml.bind.JAXBException;

import database.DatabaseAccessor;
import models.rs.gov.parlament.korisnici.Gradjani;
import models.rs.gov.parlament.korisnici.GradjaninTip;
import models.rs.gov.parlament.korisnici.Korisnici;
import play.mvc.Controller;
import converter.Converter;
import converter.MarshallType;
import converter.UnmarshallType;

/**
 * Created by aleksandar on 7.6.16..
 */
public class Users extends Controller {

    private static DatabaseAccessor db = DatabaseAccessor.getInstance();
    private static final String USERS_DOC_ID = "/parliament/users.xml";

    public static void login(GradjaninTip citizen) throws IOException, JAXBException {
        Korisnici users = fetchUsersFromDatabase();
        List<Gradjani> citizens = users.getGradjani();
        Gradjani citizensList = citizens.get(0);

        for(GradjaninTip c: citizensList.getGradjanin()) {
            if(c.getEmail().equals(citizen.getIme())) {       // TODO: ime has to be changed to email!
                renderText("User with email: " + citizen.getIme() + " found in database records.");
            }
        }

        renderText("User with email: " + citizen.getIme() + " was not found in database records.");
    }

    public static void addNewUser(GradjaninTip citizen) throws JAXBException {
        Korisnici users = fetchUsersFromDatabase();
        List<Gradjani> citizens = users.getGradjani();
        Gradjani citizensList = citizens.get(0);

        citizensList.getGradjanin().add(citizen);

        writeChangesToDatabase(users);

        renderHtml("Added to database: Name: "+ citizen.getIme() + ", "
                + "Surname: " + citizen.getPrezime() + ", Email: " + citizen.getEmail()
                + "<br/><a>Verify the content at: http://147.91.177.194:8000/v1/documents?database=Tim16&uri=" + USERS_DOC_ID + "</a>");

    }

    public static void deleteUser(GradjaninTip citizen) throws JAXBException {
        Korisnici users = fetchUsersFromDatabase();                 // TODO: change model at once! :)
        List<Gradjani> citizensCollection = users.getGradjani();
        Gradjani citizensList = citizensCollection.get(0);
        List<GradjaninTip> citizens = citizensList.getGradjanin();

        for(int i = 0; i < citizens.size(); i++) {
            if(citizens.get(i).getEmail().equals(citizen.getIme())) {       // TODO: ime has to be changed to email!
                citizensList.getGradjanin().remove(i);
                writeChangesToDatabase(users);
                renderText("Deleted user with email: " + citizen.getIme() + " from database records.");
            }
        }
        renderText("User with email: " + citizen.getIme() + " was not found in database records.");
    }

    public static void updateUser(GradjaninTip citizen) throws JAXBException {
        Korisnici users = fetchUsersFromDatabase();
        List<Gradjani> citizensCollection = users.getGradjani();
        Gradjani citizensList = citizensCollection.get(0);
        List<GradjaninTip> citizens = citizensList.getGradjanin();

        for(int i = 0; i < citizens.size(); i++) {
            if(citizens.get(i).getEmail().equals(citizen.getEmail())) {
                citizensList.getGradjanin().get(i).setIme(citizen.getIme());
                citizensList.getGradjanin().get(i).setPrezime(citizen.getPrezime());
                writeChangesToDatabase(users);
                renderText("Updated user with email: " + citizen.getEmail() + " at database records.");
            }
        }
        renderText("User with email: " + citizen.getEmail() + " was not found in database records.");

    }

    @GET
    public static Korisnici fetchUsersFromDatabase() throws JAXBException {
        System.out.println("[INFO] Fetching users form database...");

        String inputUsersXmlString = DatabaseAccessor.readXmlFromDatabase(USERS_DOC_ID);

        return  (Korisnici) Converter.unmarshall(UnmarshallType.FROM_STRING, inputUsersXmlString, Korisnici.class);
    }

    private static void writeChangesToDatabase(Korisnici users) throws JAXBException {
        System.out.println("[INFO] Writing 'users' changes to database...");

        String outputUsersXmlString = Converter.marshall(MarshallType.TO_STRING, users, "");

        DatabaseAccessor.writeXmlToDatabase(USERS_DOC_ID, outputUsersXmlString);
    }

}
