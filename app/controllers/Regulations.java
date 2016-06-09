package controllers;

import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.StringHandle;
import database.DatabaseAccessor;
import play.mvc.Controller;

import javax.xml.bind.JAXBException;

/**
 * Created by aleksandar on 8.6.16..
 */
public class Regulations extends Controller {

    private static DatabaseAccessor db = DatabaseAccessor.getInstance();
    private static final String COLLECTION_ID = "/parliament/regulations";

    /**
     * Adds new regulation to collection defined in COLLECTION_ID of {@link Regulations} class with
     * automatically generated document ID.
     * @param regulationString Regulation text which is converted and added to database.
     * @throws JAXBException
     */
    public static void addRegulation(String regulationString) throws JAXBException{
        StringHandle handle = new StringHandle(regulationString);

        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add(COLLECTION_ID);

        DocumentUriTemplate template = db.xmlManager.newDocumentUriTemplate("xml");
        template.setDirectory("/parliament/regulations/");

        DocumentDescriptor desc = db.xmlManager.create(template, metadata, handle);

        renderText("[INFO] Added regulation with docId: " + desc.getUri() + " to "
                 + "database collection: " + COLLECTION_ID);
    }

}
