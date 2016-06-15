package database;

import models.rs.gov.parlament.propisi.Propis;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by aleksandar on 14.6.16..
 */
public class DatabaseQueryTest {
    @Test
    public void metadataSearch() throws Exception {
        DatabaseAccessor.getInstance();
        HashMap<String, Object> results = DatabaseQuery.metadataSearch("ZDRAVSTVO", "O PROMETU NEPOKRETNOSTI t123", "PREDLOZEN", "PROPIS", "p0@parlament.rs",
                "whatfuckever", "some collection", "fuck", "fuckenzi", "fuck", "shure", "yep", "ok",
                "ooo", "fff", "sss", "ssda", 0, 0, 0, 0, 0, 0);

    }

    @Test
    public void search() throws Exception {
        DatabaseAccessor.getInstance();
        HashMap<String, Object> results = DatabaseQuery.search("populacija", "", Propis.class);
        System.out.println("** Results size: " + results.size());
        Assert.assertTrue(!results.isEmpty());      // has results
    }

    @Test
    public void writeNewXMl() {
        DatabaseAccessor.getInstance();
        String str = "\n" +
                "\n" +
                "<?xml  version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Propis xsi:schemaLocation=\"http://www.parlament.gov.rs/propisi file:/E:/Users/NemanjaM/Documents/Practice/XML/Projekat/XML-NoSQL-DataBase-App/xml_schema/propisi.xsd\" Uri_propisa=\"1\" Tip_dokumenta=\"PROPIS\" xmlns=\"http://www.parlament.gov.rs/propisi\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "<Preambula>\n" +
                "<Status>PREDLOZEN</Status>\n" +
                "<Predlagac>p0@parlament.rs</Predlagac>\n" +
                "<Broj_glasova_za>0</Broj_glasova_za>\n" +
                "<Broj_glasova_protiv>0</Broj_glasova_protiv>\n" +
                "<Broj_glasova_uzdrzano>0</Broj_glasova_uzdrzano>\n" +
                "</Preambula>\n" +
                "<Naziv>O PROMETU NEPOKRETNOSTI</Naziv>\n" +
                "<Sadrzaj>\n" +
                "<Glava Oznaka_glave=\"1\" Naziv_glave=\"Osnovne odredbe\">\n" +
                "<Clan Oznaka_clana=\"1\">\n" +
                "<Stav Oznaka_stava=\"1\">\n" +
                "                    Ovim zakonom uređuje se: zaštita, upravljanje, lov, korišćenje i unapređivanje populacija divljači u \n" +
                "                    lovištu; zaštita, očuvanje i unapređivanje staništa divljači; zaštita, uređivanje i održavanje lovišta i druga pitanja \n" +
                "                    od značaja za divljač i lovstvo.\n" +
                "                </Stav>\n" +
                "<Stav Oznaka_stava=\"2\">\n" +
                "                    Divljač je prirodno bogatstvo i imovina Republike Srbije koja se koristi pod uslovima i na način predviđen ovim zakonom.\n" +
                "                </Stav>\n" +
                "</Clan>\n" +
                "<Clan Oznaka_clana=\"2\">\n" +
                "<Stav Oznaka_stava=\"3\">\n" +
                "                    Divljač je prirodno bogatstvo i imovina Republike Srbije koja se koristi pod uslovima i na način predviđen ovim zakonom.\n" +
                "                </Stav>\n" +
                "</Clan>\n" +
                "<Clan Oznaka_clana=\"3\">\n" +
                "<Stav Oznaka_stava=\"4\">\n" +
                "                    Divljač je prirodno bogatstvo i imovina Republike Srbije koja se koristi pod uslovima i na način predviđen ovim zakonom.                     \n" +
                "                </Stav>\n" +
                "</Clan>\n" +
                "<Clan Oznaka_clana=\"4\">\n" +
                "<Stav Oznaka_stava=\"5\">\n" +
                "                    Cilj ovog zakona je<Referenca Uri_propisa=\"#\">Predlog zakona</Referenca> obezbeđivanje održivog gazdovanja populacijama divljači i \n" +
                "                    njihovih staništa na način i u obimu kojim se trajno održava i unapređuje vitalnost \n" +
                "                    populacija divljači, proizvodna sposobnost staništa i biološka raznovrsnost, čime se \n" +
                "                    postiže ispunjavanje ekonomskih, ekoloških i socijalnih funkcija lovstva.\n" +
                "                </Stav>\n" +
                "<Stav Oznaka_stava=\"6\">\n" +
                "                    Radi ostvarivanja i zaštite prava i interesa lovnih radnika i unapređivanja lovstva u skladu sa \n" +
                "                    održivim gazdovanjem populacijama divljači, opštim interesom i opšteprihvaćenim međunarodnim standardima \n" +
                "                    u lovstvu osniva se Lovačka komora (u daljem tekstu: Komora), \n" +
                "                    kao profesionalna organizacija, sa pravima i obavezama utvrđenim ovim zakonom i statutom Komore.\n" +
                "                </Stav>\n" +
                "</Clan>\n" +
                "<Clan Oznaka_clana=\"5\">\n" +
                "<Stav Oznaka_stava=\"7\">\n" +
                "                    Zabranjeno je:\n" +
                "                    <Tacka Oznaka_tacke=\"1\">ugrožavati opstanak divljači u prirodi i njena staništa;</Tacka>\n" +
                "<Tacka Oznaka_tacke=\"2\">proganjati, zlostavljati, namerno povređivati i uznemiravati divljač;</Tacka>\n" +
                "<Tacka Oznaka_tacke=\"3\">zarobljavati i držati divljač u zatvorenom ili ograđenom prostoru, namerno uništavati mesto za \n" +
                "                        razmnožavanje i odmor divljači, uzimati jaja od divljači, kao i sakupljati jaja zaštićenih vrsta ptica;</Tacka>\n" +
                "<Tacka Oznaka_tacke=\"4\">unositi u lovište divljač iz parkova divljači, zooloških vrtova i sa farmi divljači, kao i oštećenih jedinki divljači;</Tacka>\n" +
                "</Stav>\n" +
                "</Clan>\n" +
                "<Clan Oznaka_clana=\"6\">\n" +
                "<Stav Oznaka_stava=\"8\">\n" +
                "                    Korisnik divljači dužan je da u slučajevima iz stava 1. ovog člana postupa na human način.\n" +
                "                </Stav>\n" +
                "<Stav Oznaka_stava=\"9\">\n" +
                "                    Divljač se može unositi u lovište samo ako se njenim unošenjem ne ugrožava biološka \n" +
                "                    ravnoteža i biološka raznovrsnost.\n" +
                "                </Stav>\n" +
                "<Stav Oznaka_stava=\"10\">\n" +
                "                    Zabrane iz člana 22. tač. ovog zakona ne odnose se na divljač ugroženu od \n" +
                "                    elementarnih nepogoda, u slučajevima sprečavanja i suzbijanja zaraznih bolesti, \n" +
                "                    organizovanog hvatanja povređene divljači, naseljavanja divljači i ispitivanja \n" +
                "                    urođenih osobina lovačkih pasa, kao i na divljač za potrebe naučnog istraživanja, \n" +
                "                    nastave, zooloških vrtova i muzeja, ako je za to data saglasnost Ministarstva.\n" +
                "                </Stav>\n" +
                "</Clan>\n" +
                "<Clan Oznaka_clana=\"7\">\n" +
                "<Stav Oznaka_stava=\"11\">\n" +
                "                    Ministar i ministar nadležan za poslove zaštite životne sredine sporazumno proglašavaju \n" +
                "                    lovostajem zaštićene vrste divljači, trajanje lovne sezone na lovostajem zaštićene vrste \n" +
                "                    divljači u otvorenim i ograđenim lovištima, ograđenim delovima lovišta i poligonima za \n" +
                "                    lov divljači, kao i mere zaštite i regulisanja brojnosti populacija trajno zaštićenih i \n" +
                "                    lovostajem zaštićenih vrsta divljači, a na osnovu procene ugroženosti pojedinih vrsta, \n" +
                "                    brojnosti populacija i obaveza iz potvrđenih međunarodnih ugovora.\n" +
                "                </Stav>\n" +
                "</Clan>\n" +
                "</Glava>\n" +
                "<Glava Oznaka_glave=\"2\">\n" +
                "<Clan Oznaka_clana=\"8\">\n" +
                "<Stav Oznaka_stava=\"12\">\n" +
                "                    Redovnu proveru kvaliteta stručnog rada imaoca licence za izradu planskih dokumenata vrši \n" +
                "                    Ministarstvo, a na teritoriji autonomne pokrajine nadležni pokrajinski organ, i to u \n" +
                "                    postupku davanja saglasnosti na planske dokumente.\n" +
                "                </Stav>\n" +
                "</Clan>\n" +
                "<Clan Oznaka_clana=\"9\">\n" +
                "<Stav Oznaka_stava=\"13\">\n" +
                "                    Skupština Komore bira između svojih članova predsednika, kao i sastav upravnog odbora, \n" +
                "                    nadzornog odbora, organa Komore koji rešava o izdavanju i oduzimanju licence i Etičkog \n" +
                "                    komiteta, u čijim sastavima moraju biti predstavnici Ministarstva i autonomne pokrajine \n" +
                "                    koji su članovi Komore.\n" +
                "                </Stav>\n" +
                "</Clan>\n" +
                "<Clan Oznaka_clana=\"10\">\n" +
                "<Stav Oznaka_stava=\"14\">\n" +
                "                    Za osnivanje i početak rada Komore sredstva se obezbeđuju u budžetu Republike Srbije.\n" +
                "                </Stav>\n" +
                "</Clan>\n" +
                "<Clan Oznaka_clana=\"11\">\n" +
                "<Stav Oznaka_stava=\"15\">\n" +
                "                    Komora ima svojstvo pravnog lica koje stiče registracijom u Registar privrednih subjekata, \n" +
                "                    u skladu sa zakonom kojim se uređuje registracija privrednih subjekata.\n" +
                "                </Stav>\n" +
                "</Clan>\n" +
                "</Glava>\n" +
                "</Sadrzaj>\n" +
                "<Prilozi>\n" +
                "</Prilozi>\n" +
                "</Propis>";
        DatabaseAccessor.writeXmlToDatabase("/parliament/regulations/4702994916404006768.xml", str);
    }

}