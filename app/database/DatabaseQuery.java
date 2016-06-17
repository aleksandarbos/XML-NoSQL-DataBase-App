package database;

import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.io.StringHandle;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class DatabaseQuery {

	private static TransformerFactory transformerFactory;

	static {
		transformerFactory = TransformerFactory.newInstance();
	}

	/**
	 * Searches database entries with custom criteria, collection and specifies
	 * result type.
	 *
	 * @param criteria
	 *            Custom search criteria.
	 * @param collection
	 *            Collection where records will be queried.
	 * @param resultClass
	 * @return
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	public static HashMap<String, Object> search(String criteria, String collection, Class resultClass)
			throws FileNotFoundException, JAXBException {
		DatabaseAccessor.getInstance();

		HashMap<String, Object> returnValues = new HashMap<String, Object>();
		// Initialize query manager
		QueryManager queryManager = DatabaseAccessor.client.newQueryManager();

		// Query definition is used to specify Google-style query string
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();

		if (!criteria.equals(""))
			queryDefinition.setCriteria(criteria);

		// Search within a specific collection
		if (!collection.equals(""))
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

			String readXml = readXmlFromDatabase(docUri);
			Object resultObject = Converter.unmarshall(UnmarshallType.FROM_STRING, readXml, resultClass);

			returnValues.put(docUri, resultObject);

		}

		// DatabaseAccessor.client.release(); TODO: inspect clients

		return returnValues;
	}

	/**
	 * Searches by metadata invoking XQuery search.
	 *
	 * @return HashMap<String, Object> where k=>docId, and value is instance of
	 *         => Amandman or Propis class.
	 * @throws IOException
	 * @throws JAXBException
	 */
	public static HashMap<String, Object> metadataSearch(String documentName, String documentStatus,
														 String documentType, String user, String authority, String collection, String nominatedDateFrom,
														 String nominatedDateTo, String adoptionDateFrom, String adoptionDateTo, String announcementDateFrom,
														 String announcementDateTo, String inuseDateFrom, String inuseDateTo, String withdrawalDateFrom,
														 String withdrawalDateTo, int votesYesFrom, int votesYesTo, int votesNoFrom, int votesNoTo, int votesOffFrom,
														 int votesOffTo) {
		StringBuilder query = new StringBuilder();
		HashMap<String, Object> results = new HashMap<String, Object>();
		String collectionCriteria = "";
		String namespaceCriteria = "";
		int criteriaCnt = 0;

		if (documentType.equals("regulation")) {
			collectionCriteria = "regulations";
			namespaceCriteria = "propisi";
			documentType = "PROPIS";
		} else {
			collectionCriteria = "amendments";
			namespaceCriteria = "amandmani";
			documentType = "AMANDMAN";
		}

		query.append("declare namespace pp = \"http://www.parlament.gov.rs/" + namespaceCriteria + "\";\n"
				+ "for $x in collection(\"/parliament/" + collectionCriteria + "\")\n" + "let $y := fn:root($x)\n"
				+ "where $y//");
		if (!documentType.equals("")) {
			criteriaCnt++;
			query.append("@Tip_dokumenta = \"" + documentType + "\"\n");
		}
		if (documentName != null && !documentName.equals(""))
			query.append(checkAnd(++criteriaCnt) + " $y//pp:Naziv/text() = \"" + documentName + "\" \n");
		if (!documentStatus.equals(""))
			query.append(checkAnd(++criteriaCnt) + " $y//pp:Preambula/pp:Status/text() = \"" + documentStatus + "\"\n");
		if (user != null && !user.equals(""))
			query.append(checkAnd(++criteriaCnt) + " $y//pp:Preambula/pp:Predlagac/text() = \"" + user + "\"\n");
		if (votesYesFrom != 0)
			query.append(checkAnd(++criteriaCnt) + " $y//pp:Preambula/pp:Broj_glasova_za >= " + votesYesFrom + "\n");
		if (votesYesTo != 0)
			query.append(checkAnd(++criteriaCnt) + " $y//pp:Preambula/pp:Broj_glasova_za <= " + votesYesTo + "\n");
		if (votesNoFrom != 0)
			query.append(checkAnd(++criteriaCnt) + " $y//pp:Preambula/pp:Broj_glasova_protiv >= " + votesNoFrom + "\n");
		if (votesNoTo != 0)
			query.append(checkAnd(++criteriaCnt) + " $y//pp:Preambula/pp:Broj_glasova_protiv <= " + votesNoTo + "\n");
		if (votesOffFrom != 0)
			query.append(checkAnd(++criteriaCnt) + " $y//pp:Preambula/pp:Broj_glasova_za >= " + votesOffFrom + "\n");
		if (votesOffTo != 0)
			query.append(checkAnd(++criteriaCnt) + " $y//pp:Preambula/pp:Broj_glasova_za <= " + votesOffTo + "\n");
		query.append("return $y\n");
		System.out.println(query.toString());

		Vector<String> searchResults;
		try {
			searchResults = XQueryInvoker.execute(query.toString());
			for (String docStr : searchResults) {
				Object docObj;
				if (docStr.contains("Tip_dokumenta=\"PROPIS\"")) {
					docObj = Converter.unmarshall(UnmarshallType.FROM_STRING, docStr, Propis.class);
					results.put(((Propis) docObj).getUriPropisa(), (Propis) docObj);
				} else {
					docObj = Converter.unmarshall(UnmarshallType.FROM_STRING, docStr, Amandman.class);
					results.put(((Amandman) docObj).getUriAmandmana(), (Amandman) docObj);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return results;
	}

	/**
	 * Reads xml file from database and returns it as a string.
	 *
	 * @param docId
	 *            Resource location.
	 * @return Converted found xml to string.
	 */
	public static String readXmlFromDatabase(String docId) {
		DOMHandle content = new DOMHandle();
		DatabaseAccessor.getInstance();
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();

		System.out.println("[INFO] Retrieving \"" + docId + "\" from "
				+ (DatabaseAccessor.props.database.equals("") ? "default" : DatabaseAccessor.props.database)
				+ " database.");

		DatabaseAccessor.xmlManager.read(docId, metadata, content);

		return content.toString();
	}
	
	public static Amandman readAmendmentFromDatabase(String amandmentId){
        String xmlDocString = DatabaseQuery.readXmlFromDatabase(amandmentId);
		return (Amandman) Converter.unmarshall(UnmarshallType.FROM_STRING, xmlDocString, Amandman.class);
	}
	
	public static Propis readRegulationFromDatabase(String regulationId){
        String xmlDocString = DatabaseQuery.readXmlFromDatabase(regulationId);
		return (Propis) Converter.unmarshall(UnmarshallType.FROM_STRING, xmlDocString, Propis.class);
	}

	public static void writeXmlToDatabase(String docId, String xmlFile) {
		StringHandle content = new StringHandle();
		DatabaseAccessor.getInstance();
		content.set(xmlFile);

		DatabaseAccessor.xmlManager.write(docId, content);
		System.out.println("[INFO] Overwrite: " + docId + ", in database.");
	}

	private static void removeXmlFromDatabase(String docId) {
		String query = "xdmp:document-delete(\"" + docId + "\")";
		try {
			XQueryInvoker.execute(query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void removeDocumentFromDatabase(String docId) {

		String documentType = docId.split("/")[2];
		if (documentType.equals("amendments")) {
			removeXmlFromDatabase(docId);
			return;
		}
		List<Amandman> amendments = searchAmandmentsByRegulationId(docId);

		for (Amandman amendment : amendments) {
			String uri = amendment.getUriAmandmana();
			removeXmlFromDatabase(uri);
		}
		removeXmlFromDatabase(docId);
	}

	/**
	 * Serializes DOM tree to an arbitrary OutputStream.
	 *
	 * @param node
	 *            a node to be serialized
	 * @param out
	 *            an output stream to write the serialized DOM representation to
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

	public static List<Object> searchByUser(String documentType, String user) {
		StringBuilder query = new StringBuilder();
		List<Object> results = new ArrayList();
		String collectionCriteria = "";
		String namespaceCriteria = "";
		int criteriaCnt = 0;

		if (documentType.equals("regulation")) {
			collectionCriteria = "regulations";
			namespaceCriteria = "propisi";
			documentType = "PROPIS";
		} else {
			collectionCriteria = "amendments";
			namespaceCriteria = "amandmani";
			documentType = "AMANDMAN";
		}

		query.append("declare namespace pp = \"http://www.parlament.gov.rs/" + namespaceCriteria + "\";\n"
				+ "for $x in collection(\"/parliament/" + collectionCriteria + "\")\n" + "let $y := fn:root($x)\n"
				+ "where $y//");
		if (!documentType.equals("")) {
			criteriaCnt++;
			query.append("@Tip_dokumenta = \"" + documentType + "\"\n");
		}
		query.append(checkAnd(++criteriaCnt) + " $y//pp:Preambula/pp:Predlagac/text() = \"" + user + "\"\n");
		query.append("return $y\n");

		Vector<String> searchResults;
		try {
			searchResults = XQueryInvoker.execute(query.toString());
			for (String docStr : searchResults) {
				Object docObj;
				if (docStr.contains("Tip_dokumenta=\"PROPIS\"")) {
					docObj = Converter.unmarshall(UnmarshallType.FROM_STRING, docStr, Propis.class);
					results.add(docObj);
				} else {
					docObj = Converter.unmarshall(UnmarshallType.FROM_STRING, docStr, Amandman.class);
					results.add(docObj);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return results;
	}

	public static List<Amandman> searchAmandmentsByRegulationId(String regId) {
		StringBuilder query = new StringBuilder();
		List<Amandman> results = new ArrayList();

		query.append("declare namespace ap = \"http://www.parlament.gov.rs/amandmani\";\n"
				+ "for $x in collection(\"/parliament/amendments\")\n" + "let $y := fn:root($x)\n"
				+ "where ");
		query.append("$y//ap:Uri_propisa/text() = \"" + regId + "\"\n return $y\n");

		Vector<String> searchResults;
		try {
			searchResults = XQueryInvoker.execute(query.toString());
			for (String docStr : searchResults) {
				Amandman docObj = (Amandman) Converter.unmarshall(UnmarshallType.FROM_STRING, docStr, Amandman.class);
				results.add(docObj);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return results;
	}

	public static List<Object> searchByStatus(String status, String type) {
		StringBuilder query = new StringBuilder();
		List<Object> results = new ArrayList();
		String namespace, location, prefix;
		
		if (type.equals("REGULATIONS")) {
			namespace = "propisi";
			location = "regulations";
			prefix = "pp";
		} else {
			namespace = "amandmani";
			location = "amendments";
			prefix = "ap";
		}

		query.append("declare namespace " + prefix + " = \"http://www.parlament.gov.rs/" + namespace + "\";\n"
				+ "for $x in collection(\"/parliament/" + location + "\")\n" + "let $y := fn:root($x)\n"
				+ "where ");
		query.append("$y//"+prefix+":Preambula/"+prefix+":Status/text() = \"" + status + "\"\n return $y\n");

		Vector<String> searchResults;
		try {
			searchResults = XQueryInvoker.execute(query.toString());
			for (String docStr : searchResults) {
				Object docObj;
				if (type.equals("REGULATIONS"))
					docObj = Converter.unmarshall(UnmarshallType.FROM_STRING, docStr, Propis.class);
				else
					docObj = Converter.unmarshall(UnmarshallType.FROM_STRING, docStr, Amandman.class);
				results.add(docObj);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return results;
	}

	private static String checkAnd(int criteriaCnt) {
		if (criteriaCnt > 1)
			return "and";
		return "";
	}
}
