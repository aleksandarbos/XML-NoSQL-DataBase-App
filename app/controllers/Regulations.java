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

    public static void addNewRegulation(String regulationName, String user, String regulationContent) throws JAXBException{
        RegulationsDAO.addRegulation(regulationContent);
        show();
    }

}
