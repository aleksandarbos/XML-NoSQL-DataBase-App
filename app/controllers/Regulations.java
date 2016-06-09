package controllers;

import dal.RegulationsDAO;
import play.mvc.Controller;

import javax.xml.bind.JAXBException;

/**
 * Created by aleksandar on 8.6.16..
 */
public class Regulations extends Controller {

    public static void show(){
        render();
    }

    public static void create(String regulationName, String user, String regulationContent) throws JAXBException{
    	System.out.println("dodaj akt: " + regulationName + ", predlozio korisnik: " + user + ", sadrzaj dokumenta: "+regulationContent);
        //RegulationsDAO.addRegulation(regulationContent);
        show();
    }

}
