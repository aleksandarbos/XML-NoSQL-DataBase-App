package dal;

import converter.Converter;
import converter.types.UnmarshallType;
import database.DatabaseAccessor;
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
        editingPart.setUriPropisa("/parliament/regulations/2991373610789630903.xml");
        editingPart.setOznakaClana(6);
        editingPart.setOznakaStava(1);
        editingPart.setOznakaTacke(1);

        SadrzajTip editingContent = new SadrzajTip();
        editingContent.getContent();
        editingContent.getContent().add(new String("***CLAN 6 STAV 1 TACKA 2 ***"));
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

    @Test
    public void writeDoDatabase() {
        DatabaseAccessor.getInstance();
        String str = "<?xml  version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Propis Uri_propisa=\"/parliament/regulations/\" Tip_dokumenta=\"PROPIS\" xmlns=\"http://www.parlament.gov.rs/propisi\">\n" +
                "\t<Preambula>\n" +
                "\t\t<Status>PREDLOZEN</Status>\n" +
                "\t\t<Predlagac>p0@parlament.rs</Predlagac>\n" +
                "\t\t<Broj_glasova_za>0</Broj_glasova_za>\n" +
                "\t\t<Broj_glasova_protiv>0</Broj_glasova_protiv>\n" +
                "\t\t<Broj_glasova_uzdrzano>0</Broj_glasova_uzdrzano>\n" +
                "\t</Preambula>\n" +
                "\t<Naziv>Neko mnogo fensi dugacko ime za akt</Naziv>\n" +
                "\t<Sadrzaj>\n" +
                "\t\t<Glava Oznaka_glave=\"1\" Naziv_glave=\"Osnovne odredbe\">\n" +
                "\t\t\t<Clan Oznaka_clana=\"1\">\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"1\">\n" +
                "                    Ovim zakonom uređuje se: zaštita, upravljanje, lov, korišćenje i unapređivanje populacija divljači u \n" +
                "                    lovištu; zaštita, očuvanje i unapređivanje staništa divljači; zaštita, uređivanje i održavanje lovišta i druga pitanja \n" +
                "                    od značaja za divljač i lovstvo.\n" +
                "                </Stav>\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"2\">\n" +
                "                    Divljač je prirodno bogatstvo i imovina Republike Srbije koja se koristi pod uslovima i na način predviđen ovim zakonom.\n" +
                "                </Stav>\n" +
                "\t\t\t</Clan>\n" +
                "\t\t\t<Clan Oznaka_clana=\"2\">\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"3\">\n" +
                "                    Divljač je prirodno bogatstvo i imovina Republike Srbije koja se koristi pod uslovima i na način predviđen ovim zakonom.\n" +
                "                </Stav>\n" +
                "\t\t\t</Clan>\n" +
                "\t\t\t<Clan Oznaka_clana=\"3\">\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"4\">\n" +
                "                    Divljač je prirodno bogatstvo i imovina Republike Srbije koja se koristi pod uslovima i na način predviđen ovim zakonom.                     \n" +
                "                </Stav>\n" +
                "\t\t\t</Clan>\n" +
                "\t\t\t<Clan Oznaka_clana=\"4\">\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"5\">\n" +
                "                    Cilj ovog zakona je\n" +
                "                    \n" +
                "                    <Referenca Uri_propisa=\"#\">Predlog zakona</Referenca> obezbeđivanje održivog gazdovanja populacijama divljači i \n" +
                "                    njihovih staništa na način i u obimu kojim se trajno održava i unapređuje vitalnost \n" +
                "                    populacija divljači, proizvodna sposobnost staništa i biološka raznovrsnost, čime se \n" +
                "                    postiže ispunjavanje ekonomskih, ekoloških i socijalnih funkcija lovstva.\n" +
                "                </Stav>\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"6\">\n" +
                "                    Radi ostvarivanja i zaštite prava i interesa lovnih radnika i unapređivanja lovstva u skladu sa \n" +
                "                    održivim gazdovanjem populacijama divljači, opštim interesom i opšteprihvaćenim međunarodnim standardima \n" +
                "                    u lovstvu osniva se Lovačka komora (u daljem tekstu: Komora), \n" +
                "                    kao profesionalna organizacija, sa pravima i obavezama utvrđenim ovim zakonom i statutom Komore.\n" +
                "                </Stav>\n" +
                "\t\t\t</Clan>\n" +
                "\t\t\t<Clan Oznaka_clana=\"5\">\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"7\">\n" +
                "                    Zabranjeno je:\n" +
                "                    \n" +
                "                    \n" +
                "                    <Tacka Oznaka_tacke=\"1\">ugrožavati opstanak divljači u prirodi i njena staništa;</Tacka>\n" +
                "\t\t\t\t\t<Tacka Oznaka_tacke=\"2\">proganjati, zlostavljati, namerno povređivati i uznemiravati divljač;</Tacka>\n" +
                "\t\t\t\t\t<Tacka Oznaka_tacke=\"3\">zarobljavati i držati divljač u zatvorenom ili ograđenom prostoru, namerno uništavati mesto za \n" +
                "                        razmnožavanje i odmor divljači, uzimati jaja od divljači, kao i sakupljati jaja zaštićenih vrsta ptica;</Tacka>\n" +
                "\t\t\t\t\t<Tacka Oznaka_tacke=\"4\">unositi u lovište divljač iz parkova divljači, zooloških vrtova i sa farmi divljači, kao i oštećenih jedinki divljači;</Tacka>\n" +
                "\t\t\t\t</Stav>\n" +
                "\t\t\t</Clan>\n" +
                "\t\t\t<Clan Oznaka_clana=\"6\">\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"8\">\n" +
                "                    Korisnik divljači dužan je da u slučajevima iz stava 1. ovog člana postupa na human način.\n" +
                "                </Stav>\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"9\">\n" +
                "                    Divljač se može unositi u lovište samo ako se njenim unošenjem ne ugrožava biološka \n" +
                "                    ravnoteža i biološka raznovrsnost.\n" +
                "                </Stav>\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"10\">\n" +
                "                    Zabrane iz člana 22. tač. ovog zakona ne odnose se na divljač ugroženu od \n" +
                "                    elementarnih nepogoda, u slučajevima sprečavanja i suzbijanja zaraznih bolesti, \n" +
                "                    organizovanog hvatanja povređene divljači, naseljavanja divljači i ispitivanja \n" +
                "                    urođenih osobina lovačkih pasa, kao i na divljač za potrebe naučnog istraživanja, \n" +
                "                    nastave, zooloških vrtova i muzeja, ako je za to data saglasnost Ministarstva.\n" +
                "                </Stav>\n" +
                "\t\t\t</Clan>\n" +
                "\t\t\t<Clan Oznaka_clana=\"7\">\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"11\">\n" +
                "                    Ministar i ministar nadležan za poslove zaštite životne sredine sporazumno proglašavaju \n" +
                "                    lovostajem zaštićene vrste divljači, trajanje lovne sezone na lovostajem zaštićene vrste \n" +
                "                    divljači u otvorenim i ograđenim lovištima, ograđenim delovima lovišta i poligonima za \n" +
                "                    lov divljači, kao i mere zaštite i regulisanja brojnosti populacija trajno zaštićenih i \n" +
                "                    lovostajem zaštićenih vrsta divljači, a na osnovu procene ugroženosti pojedinih vrsta, \n" +
                "                    brojnosti populacija i obaveza iz potvrđenih međunarodnih ugovora.\n" +
                "                </Stav>\n" +
                "\t\t\t</Clan>\n" +
                "\t\t</Glava>\n" +
                "\t\t<Glava Oznaka_glave=\"2\">\n" +
                "\t\t\t<Clan Oznaka_clana=\"8\">\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"12\">\n" +
                "                    Redovnu proveru kvaliteta stručnog rada imaoca licence za izradu planskih dokumenata vrši \n" +
                "                    Ministarstvo, a na teritoriji autonomne pokrajine nadležni pokrajinski organ, i to u \n" +
                "                    postupku davanja saglasnosti na planske dokumente.\n" +
                "                </Stav>\n" +
                "\t\t\t</Clan>\n" +
                "\t\t\t<Clan Oznaka_clana=\"9\">\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"13\">\n" +
                "                    Skupština Komore bira između svojih članova predsednika, kao i sastav upravnog odbora, \n" +
                "                    nadzornog odbora, organa Komore koji rešava o izdavanju i oduzimanju licence i Etičkog \n" +
                "                    komiteta, u čijim sastavima moraju biti predstavnici Ministarstva i autonomne pokrajine \n" +
                "                    koji su članovi Komore.\n" +
                "                </Stav>\n" +
                "\t\t\t</Clan>\n" +
                "\t\t\t<Clan Oznaka_clana=\"10\">\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"14\">\n" +
                "                    Za osnivanje i početak rada Komore sredstva se obezbeđuju u budžetu Republike Srbije.\n" +
                "                </Stav>\n" +
                "\t\t\t</Clan>\n" +
                "\t\t\t<Clan Oznaka_clana=\"11\">\n" +
                "\t\t\t\t<Stav Oznaka_stava=\"15\">\n" +
                "                    Komora ima svojstvo pravnog lica koje stiče registracijom u Registar privrednih subjekata, \n" +
                "                    u skladu sa zakonom kojim se uređuje registracija privrednih subjekata.\n" +
                "                </Stav>\n" +
                "\t\t\t</Clan>\n" +
                "\t\t</Glava>\n" +
                "\t</Sadrzaj>\n" +
                "\t<Prilozi>\n" +
                "\t</Prilozi>\n" +
                "</Propis>";
        DatabaseQuery.writeXmlToDatabase("/parliament/regulations/2991373610789630903.xml", str);
    }
}