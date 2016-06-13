package controllers;

import dal.UsersDAO;
import models.rs.gov.parlament.korisnici.Korisnik;
import play.mvc.Controller;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by aleksandar on 7.6.16..
 */
public class Users extends Controller {

    private static final String USERS_DOC_ID = "/parliament/users.xml";

    public static void login(Korisnik user) throws IOException, JAXBException {
        Korisnik foundUser = UsersDAO.getCitizenFromDatabase(user);

        if(foundUser != null) {
            session.put("user", foundUser.toString()); // NOTE: only string objects allowed
            session.put("user-name", foundUser.getIme());
            session.put("user-surname", foundUser.getPrezime());
            session.put("user-type", foundUser.getTip().toString().toUpperCase());
            session.put("user-email", foundUser.getEmail());
            Overview.show();
        }
        else
            Application.index();
    }

    public static void logOut() {
        session.clear();
        Application.index();
    }

    public static void addNewUser(Korisnik user) throws JAXBException {
        UsersDAO.addNewUser(user);
        Application.index();
    }

    public static void removeUser(Korisnik user) throws JAXBException {
        UsersDAO.deleteUser(user);
        Application.index();
    }

    public static void updateUser(Korisnik user) throws JAXBException {
        UsersDAO.updateUser(user);
        Application.index();
    }

}
