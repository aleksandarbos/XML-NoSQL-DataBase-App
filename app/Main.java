import models.rs.gov.parlament.korisnici.Korisnici;
import org.apache.commons.io.IOUtils;
import util.Convertor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

/**
 * Created by aleksandar on 4.6.16..
 */
public class Main {
    public static void main(String[] args) {

        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<Korisnici xmlns=\"http://www.parlament.gov.rs/korisnici\">\n" +
                "    <Gradjani>\n" +
                "        <Gradjanin>\n" +
                "            <Ime>Aleksandar</Ime>\n" +
                "            <Prezime>Bosnjak</Prezime>\n" +
                "            <Email>aca@doctor.com</Email>\n" +
                "        </Gradjanin>\n" +
                "    </Gradjani>\n" +
                "    <Odbornici>\n" +
                "        <Odbornik>\n" +
                "            <Ime>Odborko1</Ime>\n" +
                "            <Prezime>Odborkovic1</Prezime>\n" +
                "            <Email>odb1@odb.com</Email>\n" +
                "        </Odbornik>\n" +
                "        <Odbornik>\n" +
                "            <Ime>Odborko2</Ime>\n" +
                "            <Prezime>Odborkovic2</Prezime>\n" +
                "            <Email>odb2@odb.com</Email>\n" +
                "        </Odbornik>\n" +
                "    </Odbornici>\n" +
                "    <Predsednik_skupstine>\n" +
                "        <Ime>Predsednikovic</Ime>\n" +
                "        <Prezime>Skupstinovic</Prezime>\n" +
                "        <Email>ps@ps.com</Email>\n" +
                "    </Predsednik_skupstine>\n" +
                "</Korisnici>\n";

        JAXBContext jaxbContext = null;
        Unmarshaller unmarshaller = null;
        try {
            jaxbContext = JAXBContext.newInstance(Korisnici.class);
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }


        StringReader reader = new StringReader(str);

        Korisnici users = null;
        try {
            users = (Korisnici) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        String str1 = null;
        try {
            str1 = IOUtils.toString(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(str1);

    }

}
