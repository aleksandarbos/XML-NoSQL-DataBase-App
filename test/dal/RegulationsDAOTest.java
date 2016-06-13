package dal;

import models.rs.gov.parlament.propisi.Propis;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by aleksandar on 13.6.16..
 */
public class RegulationsDAOTest {
    @Test
    public void fetchAllRegulations() throws Exception {
        HashMap<String, Propis> searchResults = RegulationsDAO.fetchAllRegulations();
        assertTrue(searchResults != null);
    }

    @Test
    public void fetchRegulationsByQuery() throws Exception {
        HashMap<String, Propis> searchResults = RegulationsDAO.fetchRegulationsByQuery("NEPOKRETNOSTI");

        for (Map.Entry<String, Propis> entry : searchResults.entrySet()) {
            Propis regulation = entry.getValue();
            assertTrue(regulation.getNaziv().equals("O PROMETU NEPOKRETNOSTI"));
        }
    }

}