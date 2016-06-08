package controllers;

import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.StringHandle;
import database.DatabaseAccessor;
import models.rs.gov.parlament.propisi.Propis;
import play.mvc.Controller;
import util.Converter;
import util.MarshallType;

import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * Created by aleksandar on 8.6.16..
 */
public class Regulations extends Controller {

    private static DatabaseAccessor db = DatabaseAccessor.getInstance();
    private static final String COLLECTION_ID = "/parliament/regulations";

    /**
     * Adds new regulation to collection defined in COLLECTION_ID of {@link Regulations} class with
     * automatically generated document ID.
     * @param regulation Regulation that will be added to database.
     * @throws JAXBException
     */
    public static void addRegulation(Propis regulation) throws JAXBException{
        String reguilationXml = Converter.marshall(MarshallType.TO_STRING, regulation, "");
        StringHandle handle = new StringHandle(reguilationXml);

        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add(COLLECTION_ID);

        DocumentUriTemplate template = db.xmlManager.newDocumentUriTemplate("pp");
        template.setDirectory("/parliament/regulations/");

        DocumentDescriptor desc = db.xmlManager.create(template, metadata, handle);

        renderText("[INFO] Added regulation with docId: " + desc.getUri() + " to "
                 + "database collection: " + COLLECTION_ID);
    }

}
