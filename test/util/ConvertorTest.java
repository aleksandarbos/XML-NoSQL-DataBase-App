package util;

import models.rs.gov.parlament.korisnici.*;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by aleksandar on 5.6.16..
 */
public class ConvertorTest {
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
            Convertor.marshall(korisnici, "kor123.xml");
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("MARSHALLING TEST FAILED!");
        }

        System.out.println("[INFO] MARSHALLING test SUCCESSFUL!");
    }

    @Test
    public void unmarshall() throws Exception {
        // TODO: Edit database schema... :)
        System.out.println("\n\n[INFO] Running UNMARSHALLING test.");

        Korisnici users = (Korisnici) Convertor.unmarshall("korisnici.xml", Korisnici.class);
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