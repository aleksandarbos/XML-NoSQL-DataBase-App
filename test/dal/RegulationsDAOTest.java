package dal;

import converter.Converter;
import converter.types.UnmarshallType;
import database.DatabaseQuery;
import models.rs.gov.parlament.amandmani.Amandman;
import models.rs.gov.parlament.amandmani.SadrzajTip;
import models.rs.gov.parlament.amandmani.TipAmandmana;
import models.rs.gov.parlament.amandmani.Preambula;
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

        Preambula preambula = new Preambula();
        amendment.setPreambula(preambula);
        amendment.getPreambula().setTip(TipAmandmana.DODAVANJE);

        Amandman.DeoZaIzmenu editingPart = new Amandman.DeoZaIzmenu();
        amendment.setDeoZaIzmenu(editingPart);
        editingPart.setUriPropisa("/parliament/regulations/17216008199143224158.xml");
        editingPart.setOznakaClana(5);
        editingPart.setOznakaStava(1);
        editingPart.setOznakaTacke(1);
        editingPart.setOznakaPodtacke(1);

        SadrzajTip editingContent = new SadrzajTip();
        editingContent.getContent();
        editingContent.getContent().add(new String("+++ PODTACKA +++"));
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