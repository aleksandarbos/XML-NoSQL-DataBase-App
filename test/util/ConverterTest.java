package util;

import models.rs.gov.parlament.korisnici.*;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * Created by aleksandar on 5.6.16..
 */
public class ConverterTest {
    @Test
    public void marshall() throws Exception {
        System.out.println("[INFO] TESTING MARSHALLING.");

        ObjectFactory of = new ObjectFactory();

        Korisnici korisnici = of.createKorisnici();
        Odbornici odbornici = of.createOdbornici();
        Gradjani gradjani = of.createGradjani();

        PredsednikSkupstineTip ps = of.createPredsednikSkupstineTip();
        ps.setEmail("ps@ps.com");
        ps.setIme("Predsednikovic");
        ps.setPrezime("Skupstinovic");
        korisnici.setPredsednikSkupstine(ps);

        OdbornikTip odb1 = of.createOdbornikTip();
        odb1.setIme("Odborko1");
        odb1.setPrezime("Odborkovic1");
        odb1.setEmail("odb1@odb.com");
        odbornici.getOdbornik().add(odb1);

        OdbornikTip odb2 = of.createOdbornikTip();
        odb2.setIme("Odborko2");
        odb2.setPrezime("Odborkovic2");
        odb2.setEmail("odb2@odb.com");
        odbornici.getOdbornik().add(odb2);
        korisnici.getOdbornici().add(odbornici);

        GradjaninTip noviKorisnik = of.createGradjaninTip();
        noviKorisnik.setIme("Aleksandar123");
        noviKorisnik.setPrezime("Bosnjak123");
        noviKorisnik.setEmail("aca123@doctor.com");

        gradjani.getGradjanin().add(noviKorisnik);
        korisnici.getGradjani().add(gradjani);

        try {
            Converter.marshall(MarshallType.TO_DISK, korisnici, "./test/util/kor123.xml");
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("MARSHALLING TEST FAILED!");
        }

        System.out.println("[INFO] MARSHALLING test SUCCESSFUL!");
    }

    @Test
    public void unmarshallFromDisk() throws Exception {
        // TODO: Edit database schema... :)
        System.out.println("\n\n[INFO] Running UNMARSHALLING test FROM DISK.");

        Korisnici users = (Korisnici) Converter.unmarshall(UnmarshallType.FROM_DISK, "test/util/korisnici.xml", Korisnici.class);

        List<Gradjani> citizensList = users.getGradjani();
        Gradjani citizensFirst = citizensList.get(0);
        List<GradjaninTip> citizens = citizensFirst.getGradjanin();

        System.out.println("[INFO] List of citizens: ");
        for (GradjaninTip citizen: citizens) {
            System.out.println("\t- " + citizen.getIme() + ", " + citizen.getPrezime() + ", " + citizen.getEmail());
        }

        System.out.println("[INFO] UNMARSHALLING test SUCCESSFUL!");
    }

    @Test
    public void unmarshallFromString() throws Exception {
        System.out.println("\n\n[INFO] Running UNMARSHALLING test FROM STRING.");

        String data = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
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


        Korisnici users = (Korisnici) Converter.unmarshall(UnmarshallType.FROM_STRING, data, Korisnici.class);

        List<Gradjani> citizensList = users.getGradjani();
        Gradjani citizensFirst = citizensList.get(0);
        List<GradjaninTip> citizens = citizensFirst.getGradjanin();

        System.out.println("[INFO] List of citizens: ");
        for (GradjaninTip citizen: citizens) {
            System.out.println("\t- " + citizen.getIme() + ", " + citizen.getPrezime() + ", " + citizen.getEmail());
        }

        System.out.println("[INFO] UNMARSHALLING test SUCCESSFUL!");
    }

}