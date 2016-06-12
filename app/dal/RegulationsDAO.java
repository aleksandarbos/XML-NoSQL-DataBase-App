package dal;

import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.StringHandle;
import controllers.Regulations;
import database.DatabaseAccessor;

import javax.xml.bind.JAXBException;

/**
 * Created by aleksandar on 9.6.16..
 */
public class RegulationsDAO {

    private static final String COLLECTION_ID = "/parliament/regulations";

    /**
     * Adds new regulation to collection defined in COLLECTION_ID of {@link Regulations} class with
     * automatically generated document ID.
     * @param regulationName The title of regulation.
     * @param user The user who is uploading regulation to database records.
     * @param regulationContent Regulation xml text which is converted and added to database.
     * @throws JAXBException
     */
    public static void addRegulation(String regulationName, String user, String regulationContent) throws JAXBException{
        StringHandle handle = new StringHandle(regulationContent);

        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add(COLLECTION_ID);

        DocumentUriTemplate template = DatabaseAccessor.xmlManager.newDocumentUriTemplate("xml");
        template.setDirectory("/parliament/regulations/");

        DocumentDescriptor desc = DatabaseAccessor.xmlManager.create(template, metadata, handle);

        System.out.println("[INFO] Added regulation with docId: " + desc.getUri() + " to "
                + "database collection: " + COLLECTION_ID);
    }

    // TODO: Implement fetchAllRegulations()
}
