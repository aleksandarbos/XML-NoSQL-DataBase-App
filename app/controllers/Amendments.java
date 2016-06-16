package controllers;

import converter.Converter;
import converter.XMLController;
import converter.types.UnmarshallType;
import dal.AmendmentsDAO;
import dal.RegulationsDAO;
import models.rs.gov.parlament.amandmani.Amandman;
import models.rs.gov.parlament.amandmani.TipAmandmana;
import models.rs.gov.parlament.propisi.Propis;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

@With(Secure.class)
public class Amendments extends Controller {

    public static void show() throws IOException, JAXBException {
        String userType = session.get("user-type");

        HashMap<String, Propis> regulationsSearch = RegulationsDAO.fetchAllRegulations();
        Collection<Propis> regulations = regulationsSearch.values();
        render(userType, regulations);
    }

    public static void create(String amandmentName, String affectedRegulationUri,
                              int affectedClause, String affectedType, String user,
                              String amendmentContent) throws JAXBException, IOException {
    	
    	
        Amandman amendment = (Amandman) Converter.unmarshall(UnmarshallType.FROM_STRING, amendmentContent, Amandman.class);
        amendment.setNaziv(amandmentName);
        amendment.getDeoZaIzmenu().setUriPropisa(affectedRegulationUri);
        amendment.getPreambula().setTip(checkAmendmentType(affectedType));

        AmendmentsDAO.addAmandment(amendment);

        show();
    }
    
    public static String checkDocument(String amendmentContent) {
    	if (!XMLController.checkWellFormness(amendmentContent)) {
    		return "error1"; // lose formiran
    	} else {
    		if (!XMLController.checkValidity(amendmentContent, "amandment")) {
    			return "error2"; // nije u skladu sa semom
    		} else {
    			return "ok";
    		}
    	}
    }
    
    @Before(unless="time")
    public static void checkAccess() {
    	if (session.get("user-type").equals("GRADJANIN"))
    		Overview.show();
    }

    private static TipAmandmana checkAmendmentType(String affectedType) {
        if(affectedType.equals("IZMENA"))
            return TipAmandmana.IZMENA;
        else if(affectedType.equals("DODAVANJE"))
            return TipAmandmana.DODAVANJE;
        else
            return TipAmandmana.BRISANJE;

    }
}
