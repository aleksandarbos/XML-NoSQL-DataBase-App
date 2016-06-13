package controllers;

import converter.Converter;
import converter.types.UnmarshallType;
import dal.RegulationsDAO;
import models.rs.gov.parlament.propisi.Propis;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

import javax.xml.bind.JAXBException;

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

        Propis regulation = (Propis) Converter.unmarshall(UnmarshallType.FROM_STRING, regulationContent, Propis.class);
        regulation.setNaziv(regulationName);
        // regulation.setUser("user"); TODO: add property user

        RegulationsDAO.addRegulation(regulation);

        show();
    }

    @Before(unless="time")
    public static void checkAccess() {
    	if (session.get("user-type").equals("GRADJANIN"))
    		Overview.show();
    }

}
