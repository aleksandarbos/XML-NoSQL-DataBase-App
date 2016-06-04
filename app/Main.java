import models.rs.gov.parlament.korisnici.*;
import util.Util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by aleksandar on 4.6.16..
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("TESTING MARSHALLING. PLEASE ENTER VALUES!");

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
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter name: ");
        noviKorisnik.setIme(sc.nextLine());

        System.out.println("Enter surname: ");
        noviKorisnik.setPrezime(sc.nextLine());

        System.out.println("Enter email: ");
        noviKorisnik.setEmail(sc.nextLine());

        gradjani.getGradjanin().add(noviKorisnik);
        korisnici.getGradjani().add(gradjani);

        try {
            marshall(korisnici);
            System.out.println("MARSHALLING TEST SUCCESSFUL!");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void marshall(Korisnici korisnici) throws JAXBException {
        System.out.println("[INFO] Example 2: JAXB marshalling -Korisnici-.\n");

        // Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
        JAXBContext context = JAXBContext.newInstance("models.rs.gov.parlament.korisnici");

        // Marshaller je objekat zadužen za konverziju iz objektnog u XML model
        Marshaller marshaller = context.createMarshaller();

        // Podešavanje marshaller-a
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // Umesto System.out-a, može se koristiti FileOutputStream
        marshaller.marshal(korisnici, System.out);
        marshaller.marshal(korisnici, new File("korisnici.xml"));
        System.out.println("Marshalled file at: korisnici.xml");

    }

}
