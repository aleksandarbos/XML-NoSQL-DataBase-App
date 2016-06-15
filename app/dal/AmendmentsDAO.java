package dal;

import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.StringHandle;
import controllers.Regulations;
import converter.Converter;
import converter.types.MarshallType;
import converter.types.UnmarshallType;
import database.DatabaseAccessor;
import database.DatabaseQuery;
import models.rs.gov.parlament.amandmani.Amandman;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandar on 13.6.16..
 */
public class AmendmentsDAO {

    private static final String COLLECTION_ID = "/parliament/amendments";

    static {
        DatabaseAccessor db = DatabaseAccessor.getInstance();
    }

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

        setDocUri(desc.getUri());
    }

    /**
     * Fetches all regulations from regulations collection in database.
     * @return HashMap<String, Amandman> of found regulations, where key=>docUri and value=>amendmentObject.
     * @throws IOException
     * @throws JAXBException
     */
    public static HashMap<String, Amandman> fetchAllAmendments() throws IOException, JAXBException {
        HashMap<String, Object> searchResults = DatabaseQuery.search("", COLLECTION_ID, Amandman.class);
        HashMap<String, Amandman> returnValues = new HashMap<String, Amandman>();

        for (Map.Entry<String, Object> entry : searchResults.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            returnValues.put(key, (Amandman) value);
        }

        return returnValues;
    }

    /**
     * Helper method that adds docUri field to already created document and stores it to database.
     * @param docUri DocUri of created document.
     * @throws JAXBException
     */
    private static void setDocUri(String docUri) throws JAXBException {
        String xmlDocString = DatabaseQuery.readXmlFromDatabase(docUri);
        Amandman amendment = (Amandman) Converter.unmarshall(UnmarshallType.FROM_STRING, xmlDocString, Amandman.class);

        amendment.setUriAmandmana(docUri);

        String editedXmlDocString = Converter.marshall(MarshallType.TO_STRING, amendment, "");
        DatabaseQuery.writeXmlToDatabase(docUri, editedXmlDocString);
    }

}
