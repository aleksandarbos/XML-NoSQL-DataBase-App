package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import converter.XMLTransformation;
import dal.RegulationsDAO;
import models.rs.gov.parlament.amandmani.Amandman;
import models.rs.gov.parlament.propisi.Propis;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Overview extends Controller {

    public static void show(){
        String userType = session.get("user-type");
        List<Propis> regulations = new ArrayList<>();
        List<Amandman> amandments = new ArrayList<>();
       /* try {
			regulations = RegulationsDAO.fetchAllRegulationsXQuery();
        	amandments = AmandmentsDAO.fetchAllRegulationsXQuery();
		} catch (IOException | JAXBException e) {
			e.printStackTrace();
		}*/
        List<Object> combined = new ArrayList<>();
        combined.addAll(regulations);
        combined.addAll(amandments);
    	render(userType, combined);
    }
    
    public static void topdf(int id) {
    	System.out.println("daj pdf za dokument: " + id);
    	XMLTransformation.transformToPdf();
    	show();
    }
    
    public static void preview(int id) {
    	System.out.println("daj html za dokument: " + id);
    	XMLTransformation.transformToXhtml();
    	show();
    }
}
