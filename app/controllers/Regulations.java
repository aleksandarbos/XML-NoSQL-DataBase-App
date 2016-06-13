package controllers;

import models.rs.gov.parlament.propisi.Propis;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

import javax.xml.bind.JAXBException;
import dal.RegulationsDAO;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by aleksandar on 8.6.16..
 */
@With(Secure.class)
public class Regulations extends Controller {

    public static void show(){
        String userType = session.get("user-type");
        render(userType);
    }

    public static void create(String regulationName, String user, String regulationContent) throws JAXBException{
    	System.out.println("dodaj akt: " + regulationName + ", predlozio korisnik: " + user + ", sadrzaj dokumenta: "+regulationContent);
        RegulationsDAO.addRegulation(regulationName, user, regulationContent);
        show();
    }
    
    @Before(unless="time")
    public static void checkAccess() {
    	if (session.get("user-type").equals("GRADJANIN"))
    		Overview.show();
    }

}
