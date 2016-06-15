package controllers;

import dal.AmendmentsDAO;
import dal.RegulationsDAO;
import models.rs.gov.parlament.amandmani.Amandman;
import models.rs.gov.parlament.propisi.Propis;
import play.mvc.Controller;
import play.mvc.With;

import javax.xml.bind.JAXBException;

import converter.XMLTransformation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    
    public static void topdf(String id) {
    	System.out.println("daj pdf za dokument: " + id);
    	//InputStream download = XMLTransformation.transformToPdf(id);
    	InputStream download = XMLTransformation.transformToPdfFile(id);
    	renderBinary(download);
    }
    
    public static void preview(String  id) {
    	System.out.println("daj html za dokument: " + id);
    	String html = XMLTransformation.transformToXhtml(id);
    	renderHtml(html);
    }
}
