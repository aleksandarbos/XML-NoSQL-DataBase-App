package database;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.StringHandle;
import util.Util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by aleksandar on 4.6.16..
 */
public class DatabaseAccessor {
    private static DatabaseAccessor instance = null;

    public static Util.ConnectionProperties props;
    public static DatabaseClient client;
    public static XMLDocumentManager xmlManager;


    private  DatabaseAccessor() {
        prepareDataBase();
    }

    public static DatabaseAccessor getInstance() {
        if(instance == null) {
            instance = new DatabaseAccessor();
        }
        return instance;
    }

    /**
     * Initializes main database related objects.
     */
    private static void prepareDataBase() {

        try {
            props = Util.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize the database client
        if (props.database.equals("")) {
            System.out.println("[INFO] Using default database.");
            client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
        } else {
            System.out.println("[INFO] Using \"" + props.database + "\" database.");
            client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
        }

        // Create a document manager to work with text files.
        xmlManager = client.newXMLDocumentManager();

    }

    /**
     * Reads xml file from database and returns it as a string.
     * @param docId Resource location.
     * @return Converted found xml to string.
     */
    public static String readXmlFromDatabase(String docId) {
        DOMHandle content = new DOMHandle();
        DocumentMetadataHandle metadata = new DocumentMetadataHandle();

        System.out.println("[INFO] Retrieving \"" + docId + "\" from "
                + (props.database.equals("") ? "default" : props.database)
                + " database.");

        xmlManager.read(docId, metadata, content);

        return content.toString();
    }

    public static void writeXmlToDatabase(String docId, String xmlFile) {
        StringHandle content = new StringHandle();
        content.set(xmlFile);

        xmlManager.write(docId, content);
        System.out.println("[INFO] Ovewrite: " + docId + ", in database.");
    }

    // TODO: Implement patch method
}
