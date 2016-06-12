package controllers;

import play.mvc.Controller;
import play.mvc.With;

import javax.xml.bind.JAXBException;
import dal.RegulationsDAO;

/**
 * Created by aleksandar on 8.6.16..
 */
@With(Secure.class)
public class Regulations extends Controller {

    public static void show(){
        render();
    }

    public static void create(String regulationName, String user, String regulationContent) throws JAXBException{
    	System.out.println("dodaj akt: " + regulationName + ", predlozio korisnik: " + user + ", sadrzaj dokumenta: "+regulationContent);
        RegulationsDAO.addRegulation(regulationName, user, regulationContent);
        show();
    }

}
