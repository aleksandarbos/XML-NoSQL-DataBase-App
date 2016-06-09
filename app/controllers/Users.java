package controllers;

import dal.UsersDAO;
import database.DatabaseAccessor;
import models.rs.gov.parlament.korisnici.GradjaninTip;
import play.mvc.Controller;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by aleksandar on 7.6.16..
 */
public class Users extends Controller {

    private static final String USERS_DOC_ID = "/parliament/users.xml";

    public static void login(GradjaninTip citizen) throws IOException, JAXBException {
        if(UsersDAO.getCitizenFromDatabase(citizen) != null)
            Overview.show();
        else
            Application.index();
    }

    public static void addNewUser(GradjaninTip citizen) throws JAXBException {
        UsersDAO.addNewUser(citizen);
        Application.index();
    }

    public static void removeUser(GradjaninTip citizen) throws JAXBException {
        UsersDAO.deleteUser(citizen);
        Application.index();
    }

    public static void updateUser(GradjaninTip citizen) throws JAXBException {
        UsersDAO.updateUser(citizen);
        Application.index();
    }

}
