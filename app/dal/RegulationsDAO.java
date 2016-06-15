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
import models.rs.gov.parlament.propisi.Propis;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandar on 9.6.16..
 */
public class RegulationsDAO {

    private static final String COLLECTION_ID = "/parliament/regulations";

    /**
     * Adds new regulation to collection defined in COLLECTION_ID of {@link Regulations} class with
     * automatically generated document ID.
     * @param regulation Regulation object that will be written to database records.
     * @throws JAXBException
     */
    public static void addRegulation(Propis regulation) throws JAXBException{
        String regulationContent = Converter.marshall(MarshallType.TO_STRING, regulation, "");
        StringHandle handle = new StringHandle(regulationContent);

        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add(COLLECTION_ID);

        DocumentUriTemplate template = DatabaseAccessor.xmlManager.newDocumentUriTemplate("xml");
        template.setDirectory("/parliament/regulations/");

        DocumentDescriptor desc = DatabaseAccessor.xmlManager.create(template, metadata, handle);

        System.out.println("[INFO] Added regulation with docId: " + desc.getUri() + " to "
                + "database collection: " + COLLECTION_ID);

        setDocUri(desc.getUri());
    }

    /**
     * Fetches all regulations from regulations collection in database.
     * @return HashMap<String, Propis> of found regulations, where key=>docUri and value=>regulationObject.
     * @throws IOException
     * @throws JAXBException
     */
    public static HashMap<String, Propis> fetchAllRegulations() throws IOException, JAXBException {
        HashMap<String, Object> searchResults = DatabaseQuery.search("", COLLECTION_ID, Propis.class);
        HashMap<String, Propis> returnValues = new HashMap<String, Propis>();

        for (Map.Entry<String, Object> entry : searchResults.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            returnValues.put(key, (Propis) value);
        }

        return returnValues;
    }

    /**
     * Fetches all regulations from regulations collection in database by custom query.
     * @return HashMap<String, Propis> of found regulations, where key=>docUri and value=>regulationObject.
     * @throws IOException
     * @throws JAXBException
     */
    public static HashMap<String, Object> fetchRegulationsByQuery(String query) {
        HashMap<String, Object> returnValues = null;

		try {
            returnValues = DatabaseQuery.search(query, COLLECTION_ID, Propis.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
            e.printStackTrace();
        }

        return returnValues;
    }

    public static void updateRegulation(String regulationUri, Propis regulation) {
        
    }

    public static void deleteRegulation(String regulationUri) {
        DatabaseQuery.removeXmlFromDatabase(regulationUri);
    }

    /**
     * Helper method that adds docUri field to already created document and stores it to database.
     * @param docUri DocUri of created document.
     * @throws JAXBException
     */
    private static void setDocUri(String docUri) throws JAXBException {
        String xmlDocString = DatabaseQuery.readXmlFromDatabase(docUri);
        Propis regulation = (Propis) Converter.unmarshall(UnmarshallType.FROM_STRING, xmlDocString, Propis.class);

        regulation.setUriPropisa(docUri);

        String editedXmlDocString = Converter.marshall(MarshallType.TO_STRING, regulation, "");
        DatabaseQuery.writeXmlToDatabase(docUri, editedXmlDocString);
    }
}
