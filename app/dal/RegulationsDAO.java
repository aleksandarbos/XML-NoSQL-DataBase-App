package dal;

import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.StringHandle;
import controllers.Regulations;
import converter.Converter;
import converter.UnmarshallType;
import database.DatabaseAccessor;
import database.DatabaseQuery;
import database.XQueryInvoker;
import models.rs.gov.parlament.propisi.Propis;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.*;

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

    /**
     * Fetches all regulations from regulations collection in database.
     * @return Vector of found regulations.
     * @throws IOException XQuery file not found or cannot be opened.
     * @throws JAXBException
     */
    public static Vector<Propis> fetchAllRegulationsXQuery() throws IOException, JAXBException {
        Vector<String> responsesStr = XQueryInvoker.execute("/regulations/fetch-all.xqy");
        Vector<Propis> regulations = new Vector<Propis>();
        Iterator<String> it = responsesStr.iterator();

        while(it.hasNext()) {
            Propis regulation = (Propis) Converter.unmarshall(UnmarshallType.FROM_STRING, it.next(), Propis.class);
            regulations.add(regulation);
        }

        return regulations;
    }

    /**
     * Fetches all regulations from regulations collection in database.
     * @return HashMap<String, Propis> of found regulations, where key=>docUri and value=>regulationObject.
     * @throws IOException
     * @throws JAXBException
     */
    public static HashMap<String, Propis> fetchAllRegulations() throws IOException, JAXBException {
        HashMap<String, Object> searchResults = DatabaseQuery.search("", "/parliament/regulations", Propis.class);
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
    public static HashMap<String, Propis> fetchRegulationsByQuery(String query) throws IOException, JAXBException {
        HashMap<String, Object> searchResults = DatabaseQuery.search(query, "/parliament/regulations", Propis.class);
        HashMap<String, Propis> returnValues = new HashMap<String, Propis>();

        for (Map.Entry<String, Object> entry : searchResults.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            returnValues.put(key, (Propis) value);
        }

        return returnValues;
    }


    public static void updateRegulation(Propis regulation) {
        
    }
}
