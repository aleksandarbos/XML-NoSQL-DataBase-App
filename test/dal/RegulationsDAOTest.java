package dal;

import converter.Converter;
import converter.types.UnmarshallType;
import database.DatabaseQuery;
import models.rs.gov.parlament.amandmani.Amandman;
import models.rs.gov.parlament.amandmani.SadrzajTip;
import models.rs.gov.parlament.propisi.Propis;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by aleksandar on 13.6.16..
 */
public class RegulationsDAOTest {
    @Test
    public void updateRegulation() throws Exception {
        Amandman amendment = new Amandman();
        Amandman.DeoZaIzmenu editingPart = new Amandman.DeoZaIzmenu();
        amendment.setDeoZaIzmenu(editingPart);
        editingPart.setUriPropisa("/parliament/regulations/4027267063675780869.xml");
        editingPart.setOznakaClana(3);
        editingPart.setOznakaStava(4);

        SadrzajTip editingContent = new SadrzajTip();
        editingContent.getContent();
        editingContent.getContent().add(new String("**** CLAN 3, STAV 4 ****"));
        amendment.setSadrzaj(editingContent);

        RegulationsDAO.updateRegulation(amendment);
    }

    @Test
    public void fetchAllRegulations() throws Exception {
        HashMap<String, Propis> searchResults = RegulationsDAO.fetchAllRegulations();
        assertTrue(searchResults != null);
    }

    @Test
    public void fetchRegulationsByQuery() throws Exception {
        HashMap<String, Object> searchResults = RegulationsDAO.fetchRegulationsByQuery("NEPOKRETNOSTI");

        for (Map.Entry<String, Object> entry : searchResults.entrySet()) {
            Object regulation = entry.getValue();
            assertTrue(((Propis)regulation).getNaziv().equals("O PROMETU NEPOKRETNOSTI"));
        }
    }

    @Test
    public void getMemberContent() throws JAXBException {
        String docStr = DatabaseQuery.readXmlFromDatabase("/parliament/regulations/7623921861952747138.xml");
        Propis regulation = (Propis) Converter.unmarshall(UnmarshallType.FROM_STRING, docStr, Propis.class);
    }
}