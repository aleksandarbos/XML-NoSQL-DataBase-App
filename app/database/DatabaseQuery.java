package database;

import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import converter.Converter;
import converter.types.UnmarshallType;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.HashMap;

public class DatabaseQuery {

	private static TransformerFactory transformerFactory;

	static {
		transformerFactory = TransformerFactory.newInstance();
	}

	/**
	 * Searches database entries with custom criteria, collection and specifies result type.
	 * @param criteria Custom search criteria.
	 * @param collection Collection where records will be queried.
	 * @param resultClass
	 * @return
	 * @throws FileNotFoundException
     * @throws JAXBException
     */
	public static HashMap<String, Object> search(String criteria, String collection, Class resultClass) throws FileNotFoundException, JAXBException {
		DatabaseAccessor.getInstance();

		HashMap<String, Object> returnValues = new HashMap<String, Object>();
		// Initialize query manager
		QueryManager queryManager = DatabaseAccessor.client.newQueryManager();
		
		// Query definition is used to specify Google-style query string
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();
		
		if(!criteria.equals(""))
			queryDefinition.setCriteria(criteria);
		
		// Search within a specific collection
		if(!collection.equals(""))
			queryDefinition.setCollections(collection);
		
		// Perform search
		SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());
		// Serialize search results to the standard output
		MatchDocumentSummary matches[] = results.getMatchResults();

		System.out.println("[INFO] Showing the results for: " + criteria + "\n");

		MatchDocumentSummary result;
		MatchLocation locations[];
		String text;

		for (int i = 0; i < matches.length; i++) {
			result = matches[i];
			String docUri = result.getUri();

			String readXml = DatabaseAccessor.readXmlFromDatabase(docUri);
			Object resultObject = Converter.unmarshall(UnmarshallType.FROM_STRING, readXml, resultClass);

			returnValues.put(docUri, resultObject);

		}

		// DatabaseAccessor.client.release(); TODO: inspect clients

		return returnValues;
	}

	/**
	 * Serializes DOM tree to an arbitrary OutputStream.
	 *
	 * @param node a node to be serialized
	 * @param out an output stream to write the serialized 
	 * DOM representation to
	 * 
	 */
	private static void transform(Node node, OutputStream out) {
		try {

			// Kreiranje instance objekta zaduzenog za serijalizaciju DOM modela
			Transformer transformer = transformerFactory.newTransformer();

			// Indentacija serijalizovanog izlaza
			transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			// Nad "source" objektom (DOM stablo) vrši se transformacija
			DOMSource source = new DOMSource(node);

			// Rezultujući stream (argument metode) 
			StreamResult result = new StreamResult(out);

			// Poziv metode koja vrši opisanu transformaciju
			transformer.transform(source, result);
			
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}
