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
import database.XQueryInvoker;
import models.rs.gov.parlament.amandmani.Amandman;
import models.rs.gov.parlament.amandmani.TipAmandmana;
import models.rs.gov.parlament.propisi.Propis;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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

    /**
     * Updating regulation by accepted amendment content.
     * @param amendment Amendment object with all editing content.
     */
    public static void updateRegulation(Amandman amendment) throws IOException {
        StringBuilder query = new StringBuilder();

        int numOfMember = 0;
        int numOfPosition = 0;
        int numOfPoint = 0;
        int numOfSubPoint = 0;
        int numOfBulletPoint = 0;
        int editDepth = 0;

        String regulationDocUri = "";
        String amendmentContent = "";
        StringBuilder getLastAttributeValQuery;

        TipAmandmana typeOfAmendment = null;
        Vector<String> lastIdxResults = null;
        int lastIdx = 0;

        try {
            typeOfAmendment = amendment.getPreambula().getTip();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(amendment.getDeoZaIzmenu() != null) {
            if(amendment.getDeoZaIzmenu().getOznakaClana() != 0) { numOfMember = amendment.getDeoZaIzmenu().getOznakaClana(); editDepth++; }
            if(amendment.getDeoZaIzmenu().getOznakaStava() != null) { numOfPosition = amendment.getDeoZaIzmenu().getOznakaStava(); editDepth++; }
            if(amendment.getDeoZaIzmenu().getOznakaTacke() != null) { numOfPoint = amendment.getDeoZaIzmenu().getOznakaTacke(); editDepth++; }
            if(amendment.getDeoZaIzmenu().getOznakaPodtacke() != null) { numOfSubPoint = amendment.getDeoZaIzmenu().getOznakaPodtacke(); editDepth++; }
            if(amendment.getDeoZaIzmenu().getOznakaAlineje() != null) { numOfBulletPoint = amendment.getDeoZaIzmenu().getOznakaAlineje(); editDepth++; }
        }

        regulationDocUri = amendment.getDeoZaIzmenu().getUriPropisa();
        amendmentContent = amendment.getSadrzaj().getContent().get(0).toString();

        switch (typeOfAmendment) {
            case IZMENA:
                updateRegulationByEdit(numOfMember, numOfPosition, numOfPoint,
                        numOfSubPoint, numOfBulletPoint, regulationDocUri, amendmentContent);
                break;
            case BRISANJE:
                updateRegulationByDelete(numOfMember, numOfPosition, numOfPoint,
                        numOfSubPoint, numOfBulletPoint, regulationDocUri);
                break;
            case DODAVANJE:
                updateRegulationByAdd(numOfMember, numOfPosition, amendmentContent,
                        numOfPoint, numOfSubPoint, editDepth, numOfBulletPoint,
                        regulationDocUri);
                break;
        }
    }

    private static void updateRegulationByEdit(int numOfMember, int numOfPosition,
                                               int numOfPoint, int numOfSubPoint,
                                               int numOfBulletPoint, String regulationDocUri,
                                               String amendmentContent) throws IOException {

        StringBuilder query = new StringBuilder();
        query.append("declare namespace pp = \"http://www.parlament.gov.rs/propisi\";\n" +
                     "for $node in doc(\"" + regulationDocUri + "\")\n//pp:Sadrzaj\n");

        if(numOfMember != 0) { query.append("//pp:Clan[@Oznaka_clana = " + numOfMember + "]\n"); }
        if(numOfPosition != 0) { query.append("//pp:Stav[@Oznaka_stava = " + numOfPosition + "]\n"); }
        if(numOfPoint != 0) { query.append("//pp:Tacka[@Oznaka_tacke = " + numOfPoint + "]\n"); }
        if(numOfSubPoint != 0) { query.append("//pp:Podtacka[@Oznaka_podtacke = " + numOfSubPoint + "]\n"); }
        if(numOfSubPoint != 0) { query.append("//pp:Alineja[@Oznaka_alineje = " + numOfBulletPoint + "]\n"); }

        query.append("return xdmp:node-replace($node/text(), " +
                    "text{\"" + amendmentContent + "\"});");
        //System.out.println(query.toString());
        XQueryInvoker.execute(query.toString());
    }

    private static void updateRegulationByDelete(int numOfMember, int numOfPosition,
                                               int numOfPoint, int numOfSubPoint,
                                               int numOfBulletPoint, String regulationDocUri
                                               ) throws IOException {
        StringBuilder query = new StringBuilder();
        query.append("declare namespace pp = \"http://www.parlament.gov.rs/propisi\";\n" +
                "for $node in doc(\"" + regulationDocUri + "\")\n//pp:Sadrzaj\n");

        if(numOfMember != 0) { query.append("//pp:Clan[@Oznaka_clana = " + numOfMember + "]\n"); }
        if(numOfPosition != 0) { query.append("//pp:Stav[@Oznaka_stava = " + numOfPosition + "]\n"); }
        if(numOfPoint != 0) { query.append("//pp:Tacka[@Oznaka_tacke = " + numOfPoint + "]\n"); }
        if(numOfSubPoint != 0) { query.append("//pp:Podtacka[@Oznaka_podtacke = " + numOfSubPoint + "]\n"); }
        if(numOfBulletPoint != 0) { query.append("//pp:Alineja[@Oznaka_alineje = " + numOfBulletPoint + "]\n"); }

        query.append("return xdmp:node-delete($node);");
        //System.out.println(query.toString());
        XQueryInvoker.execute(query.toString());

    }
    private static void updateRegulationByAdd(int numOfMember, int numOfPosition, String amendmentContent,
                                              int numOfPoint, int numOfSubPoint, int editDepth,
                                              int numOfBulletPoint, String regulationDocUri) throws IOException {

        StringBuilder query = new StringBuilder();
        StringBuilder getLastAttributeValQuery = new StringBuilder();
        Vector<String> lastIdxResults = null;
        int lastIdx = 0;

        query.append("declare namespace pp = \"http://www.parlament.gov.rs/propisi\";\n" +
                "for $node in doc(\"" + regulationDocUri + "\")\n//pp:Sadrzaj\n");

        getLastAttributeValQuery = new StringBuilder(query.toString()); // for another query, to get last index
        getLastAttributeValQuery.insert(getLastAttributeValQuery.indexOf("//pp:"), " return\n");

        if(numOfMember != 0) { query.append("//pp:Clan[@Oznaka_clana = " + numOfMember + "]\n"); }
        if(numOfPosition != 0) { query.append("//pp:Stav[@Oznaka_stava = " + numOfPosition + "]\n"); }
        if(numOfPoint != 0) { query.append("//pp:Tacka[@Oznaka_tacke = " + numOfPoint + "]\n"); }
        if(numOfSubPoint != 0) { query.append("//pp:Podtacka[@Oznaka_podtacke = " + numOfSubPoint + "]\n"); }
        if(numOfBulletPoint != 0) { query.append("//pp:Alineja[@Oznaka_alineje = " + numOfBulletPoint + "]\n"); }

        query.append("return xdmp:node-insert-after($node, ");

        switch (editDepth) {
            case 1: // Clan depth
                getLastAttributeValQuery.append("//pp:Clan[@Oznaka_clana]/last()");
                lastIdxResults = XQueryInvoker.execute(getLastAttributeValQuery.toString());
                if(lastIdxResults.size() > 0) {
                    lastIdx = Integer.parseInt(lastIdxResults.get(0)) + 1;
                } else {
                    lastIdx = 1;
                }
                query.append("<pp:Clan Oznaka_clana='"+lastIdx+"'>"+amendmentContent+"</pp:Clan>);");
                break;
            case 2: // Clan depth
                getLastAttributeValQuery.append("//pp:Stav[@Oznaka_stava]/last()");
                lastIdxResults = XQueryInvoker.execute(getLastAttributeValQuery.toString());
                if(lastIdxResults.size() > 0) {
                    lastIdx = Integer.parseInt(lastIdxResults.get(0)) + 1;
                } else {
                    lastIdx = 1;
                }
                query.append("<pp:Stav Oznaka_stava='"+lastIdx+"'>"+amendmentContent+"</pp:Stav>);");
                break;
            case 3: // Stav depth
                getLastAttributeValQuery.append("//pp:Tacka[@Oznaka_tacke]/last()");
                lastIdxResults = XQueryInvoker.execute(getLastAttributeValQuery.toString());
                if(lastIdxResults.size() > 0) {
                    lastIdx = Integer.parseInt(lastIdxResults.get(0)) + 1;
                } else {
                    lastIdx = 1;
                }
                query.append("<pp:Tacka Oznaka_tacke='"+lastIdx+"'>"+amendmentContent+"</pp:Tacka>);");
                break;
            case 4: // Tacka depth
                getLastAttributeValQuery.append("//pp:Podtacka[@Oznaka_podtacke]/last()");
                lastIdxResults = XQueryInvoker.execute(getLastAttributeValQuery.toString());
                if(lastIdxResults.size() > 0) {
                    lastIdx = Integer.parseInt(lastIdxResults.get(0)) + 1;
                } else {
                    lastIdx = 1;
                }
                query.append("<pp:Podtacka Oznaka_podtacke='"+lastIdx+"'>"+amendmentContent+"</pp:Podtacka>);");
                break;
            case 5: // Podtacka depth
                getLastAttributeValQuery.append("//pp:Alineja[@Oznaka_alineje]/last()");
                lastIdxResults = XQueryInvoker.execute(getLastAttributeValQuery.toString());
                if(lastIdxResults.size() > 0) {
                    lastIdx = Integer.parseInt(lastIdxResults.get(0)) + 1;
                } else {
                    lastIdx = 1;
                }
                query.append("<pp:Alineja Oznaka_alineje='"+lastIdx+"'>"+amendmentContent+"</pp:Podtacka>);");
                break;
        }
        System.out.println("\n"+query.toString()+"\n");
        XQueryInvoker.execute(query.toString());
        resetRegulationAttributes(regulationDocUri);
    }

    public static void deleteRegulation(String regulationUri) {
        DatabaseQuery.removeDocumentFromDatabase(regulationUri);
    }

    /**
     * Resets all regulation elements attribute id.
     * @param docUri Regulation docUri.
     * @throws IOException
     */
    private static void resetRegulationAttributes(String docUri) throws IOException {
        String query = "declare namespace pp = \"http://www.parlament.gov.rs/propisi\";\n" +
                "for $x at $i in doc(\""+docUri+"\")//pp:Clan\n" +
                "let $newVal as attribute() := attribute Oznaka_clana {$i}\n" +
                "return xdmp:node-replace($x/@Oznaka_clana, $newVal);\n" +
                "\n" +
                "declare namespace pp = \"http://www.parlament.gov.rs/propisi\";\n" +
                "for $x at $i in doc(\""+docUri+"\")//pp:Clan\n" +
                "for $x_1 at $i_1 in $x/pp:Stav\n" +
                "let $new_Val_1 as attribute() := attribute Oznaka_stava {$i_1}\n" +
                "return xdmp:node-replace($x_1/@Oznaka_stava, $new_Val_1);\n" +
                "\n" +
                "declare namespace pp = \"http://www.parlament.gov.rs/propisi\";\n" +
                "for $x at $i in doc(\""+docUri+"\")//pp:Clan\n" +
                "for $x_1 at $i_1 in $x/pp:Stav\n" +
                "for $x_2 at $i_2 in $x_1/pp:Tacka\n" +
                "let $new_Val_2 as attribute() := attribute Oznaka_tacke {$i_2}\n" +
                "return xdmp:node-replace($x_2/@Oznaka_tacke, $new_Val_2);\n" +
                "\n";
        XQueryInvoker.execute(query);
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
