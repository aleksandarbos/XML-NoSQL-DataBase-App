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
import models.AssemblySession;
import models.rs.gov.parlament.amandmani.Amandman;
import models.rs.gov.parlament.amandmani.StatusAmandmana;
import models.rs.gov.parlament.propisi.Propis;
import models.rs.gov.parlament.propisi.StatusAkta;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Alterations extends Controller {

    public static void show() {
        AssemblySession assemblySession = new AssemblySession();
        String userType = session.get("user-type");
        String user = session.get("user-name") + " " + session.get("user-surname");

        List<Object> documents = new ArrayList<Object>();
        List<Object> regulations = DatabaseQuery.searchByUser("regulation", user);
        List<Object> amandments = DatabaseQuery.searchByUser("amandments", user);

        for (Object regulation : regulations) {
			if (((Propis)regulation).getPreambula().getStatus()==StatusAkta.PREDLOZEN)
				documents.add(regulation);
		}
        for (Object amendment : amandments) {
			if (((Amandman)amendment).getPreambula().getStatus()==StatusAmandmana.PREDLOZEN)
				documents.add(amendment);
		}

        documents.addAll(regulations);
        documents.addAll(amandments);
        
    	render(userType, documents, assemblySession);
    }
    
    public static void delete(String id) {
    	DatabaseQuery.removeDocumentFromDatabase(id);
    	show();
    }
    
    @Before(unless="time")
    public static void checkAccess() {
    	if (session.get("user-type").equals("GRADJANIN"))
    		Overview.show();
    }
}
