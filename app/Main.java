import dal.RegulationsDAO;
import models.rs.gov.parlament.propisi.Propis;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by aleksandar on 4.6.16..
 */
public class Main {
    public static void main(String[] args) throws JAXBException, IOException {
        Vector<Propis> regulations = RegulationsDAO.fetchAllRegulations();
        System.out.println("...");
    }

}
