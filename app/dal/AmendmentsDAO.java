package dal;

import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.StringHandle;
import controllers.Regulations;
import converter.Converter;
import converter.types.MarshallType;
import database.DatabaseAccessor;
import models.rs.gov.parlament.amandmani.Amandman;

import javax.xml.bind.JAXBException;

/**
 * Created by aleksandar on 13.6.16..
 */
public class AmendmentsDAO {

    private static final String COLLECTION_ID = "/parliament/amendments";

    /**
     * Adds new amendment to collection defined in COLLECTION_ID of {@link Regulations} class with
     * automatically generated document ID.
     * @param amendment Amandment object and added to database.
     * @throws JAXBException
     */
    public static void addAmandment(Amandman amendment) throws JAXBException{

        String amendmentContent = Converter.marshall(MarshallType.TO_STRING, amendment, "");
        StringHandle handle = new StringHandle(amendmentContent);

        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add(COLLECTION_ID);

        DocumentUriTemplate template = DatabaseAccessor.xmlManager.newDocumentUriTemplate("xml");
        template.setDirectory(COLLECTION_ID + "/");

        DocumentDescriptor desc = DatabaseAccessor.xmlManager.create(template, metadata, handle);

        System.out.println("[INFO] Added amendment with docId: " + desc.getUri() + " to "
                + "database collection: " + COLLECTION_ID);
    }

}
