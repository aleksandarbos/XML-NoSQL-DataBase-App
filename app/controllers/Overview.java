package controllers;

import dal.AmendmentsDAO;
import dal.RegulationsDAO;
import models.rs.gov.parlament.amandmani.Amandman;
import models.rs.gov.parlament.propisi.Propis;
import play.mvc.Controller;
import play.mvc.With;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@With(Secure.class)
public class Overview extends Controller {

    public static void show() {
        String userType = session.get("user-type");

        HashMap<String, Propis> regulations = null;
        HashMap<String, Amandman> amandments = null;

        try {
            regulations = RegulationsDAO.fetchAllRegulations();
            amandments = AmendmentsDAO.fetchAllAmendments();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        List<Object> combined = new ArrayList<Object>();
        combined.addAll(regulations.values());
        combined.addAll(amandments.values());

        render(userType, combined);
    }
    
    public static void topdf(String docUri) {
    	System.out.println("daj pdf za dokument: " + docUri);
    	//XMLTransformation.transformToPdf();
    	show();
    }
    
    public static void preview(String docUri) {
    	System.out.println("daj html za dokument: " + docUri);
    	//XMLTransformation.transformToXhtml();
    	show();
    }
}
