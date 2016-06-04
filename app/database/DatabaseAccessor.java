package database;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import util.Util;

import java.io.IOException;

/**
 * Created by aleksandar on 4.6.16..
 */
public class DatabaseAccessor {
    private static DatabaseClient client;
    private static Util.ConnectionProperties props;
    private static XMLDocumentManager xmlManager;
    private static DatabaseAccessor instance = null;

    private  DatabaseAccessor() {
        prepareDataBase();
    }

    public static DatabaseAccessor getInstance() {
        if(instance != null) {
            instance = new DatabaseAccessor();
        }
        return instance;
    }

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
}
