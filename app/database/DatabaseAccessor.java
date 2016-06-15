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

    // TODO: Implement patch method
}
