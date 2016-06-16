package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import dal.AmendmentsDAO;
import dal.RegulationsDAO;
import database.DatabaseAccessor;
import database.DatabaseQuery;
import models.rs.gov.parlament.amandmani.Amandman;
import models.rs.gov.parlament.propisi.Propis;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Alterations extends Controller {

    public static void show() {
        String userType = session.get("user-type");
        String user = session.get("user-name") + " " + session.get("user-surname");

        List<Object> documents = new ArrayList<Object>();
        List<Object> regulations = DatabaseQuery.searchByUser("regulation", user);
        List<Object> amandments = DatabaseQuery.searchByUser("amandments", user);

        documents.addAll(regulations);
        documents.addAll(amandments);
        
    	render(userType, documents);
    }
    
    public static void delete(int id) {
    	System.out.println("obrisi dokument: " + id);
    	show();
    }
    
    @Before(unless="time")
    public static void checkAccess() {
    	if (session.get("user-type").equals("GRADJANIN"))
    		Overview.show();
    }
}
