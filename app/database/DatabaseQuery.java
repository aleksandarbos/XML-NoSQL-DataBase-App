package database;

import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import converter.Converter;
import converter.types.UnmarshallType;
import models.rs.gov.parlament.amandmani.Amandman;
import models.rs.gov.parlament.propisi.Propis;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Vector;

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
	 * Searches by metadata invoking XQuery search.
     * @return HashMap<String, Object> where k=>docId, and value is instance of => Amandman or Propis class.
     * @throws IOException
     * @throws JAXBException
     */
	public static HashMap<String, Object> metadataSearch(String documentDomain, String documentName, String documentStatus, String documentType, String user, String authority, String collection,
														 String nominatedDateFrom, String nominatedDateTo, String adoptionDateFrom, String adoptionDateTo, String announcementDateFrom, String announcementDateTo,
														 String inuseDateFrom, String inuseDateTo, String withdrawalDateFrom, String withdrawalDateTo,
														 int votesYesFrom, int votesYesTo, int votesNoFrom, int votesNoTo, int votesOffFrom, int votesOffTo) throws IOException, JAXBException {
		StringBuilder query = new StringBuilder();
		HashMap<String, Object> results = new HashMap<String, Object>();

		query.append("declare namespace pp = \"http://www.parlament.gov.rs/propisi\";\n" +
				"for $x in collection(\"/parliament/" + ((documentType.equals("PROPIS"))?"regulations":"amendments") + "\")\n" +
				"let $y := fn:root($x)\n" +
				"where $y//");
		query.append("@Tip_dokumenta = \"" + documentType+ "\"\n");
		if(documentName != null) query.append("and $y//pp:Naziv/text() = \"" + documentName + "\" \n");
		query.append("and $y//pp:Preambula/pp:Status/text() = \"" + documentStatus + "\"\n");
		if(user != null) query.append("and $y//pp:Preambula/pp:Predlagac/text() = \"" + user + "\"\n");
		if(votesYesFrom != 0 && votesNoFrom != 0) {
			query.append("and $y//pp:Preambula/pp:Broj_glasova_za >= " + votesYesFrom + "\n");
			query.append("and $y//pp:Preambula/pp:Broj_glasova_za >= " + votesYesTo + "\n");
		}
		if(votesNoFrom != 0 && votesNoTo != 0) {
			query.append("and $y//pp:Preambula/pp:Broj_glasova_protiv >= " + votesNoFrom + "\n");
			query.append("and $y//pp:Preambula/pp:Broj_glasova_protiv >= " + votesNoTo + "\n");
		}
		if(votesOffFrom != 0 && votesOffTo != 0) {
			query.append("and $y//pp:Preambula/pp:Broj_glasova_za >= " + votesOffFrom + "\n");
			query.append("and $y//pp:Preambula/pp:Broj_glasova_za >= " + votesOffTo + "\n");
		}
		query.append("return $y\n");

		Vector<String> searchResults = XQueryInvoker.execute(query.toString()); // will be invoked

		for(String docStr : searchResults) {
			Object docObj;
			if(docStr.contains("<Propis")) {
				docObj = Converter.unmarshall(UnmarshallType.FROM_STRING, docStr, Propis.class);
				results.put(((Propis)docObj).getUriPropisa(), (Propis) docObj);
			}
			else {
				docObj = Converter.unmarshall(UnmarshallType.FROM_STRING, docStr, Amandman.class);
				results.put(((Amandman)docObj).getUriAmandmana(), (Amandman) docObj);
			}
		}

		return results;
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
