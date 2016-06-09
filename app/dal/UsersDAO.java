package dal;

import converter.Converter;
import converter.MarshallType;
import converter.UnmarshallType;
import database.DatabaseAccessor;
import models.rs.gov.parlament.korisnici.Gradjani;
import models.rs.gov.parlament.korisnici.GradjaninTip;
import models.rs.gov.parlament.korisnici.Korisnici;

import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * Created by aleksandar on 9.6.16..
 */
public class UsersDAO {

    private static final String USERS_DOC_ID = "/parliament/users.xml";

    /**
     * Returns citizen object of type {@link GradjaninTip} from database. If it is not found, returns null.
     * @param citizen Citizen that contains search criteria.
     * @return  Found citizen object, or null if it is not found in database.
     * @throws JAXBException
     */
    public static GradjaninTip getCitizenFromDatabase(GradjaninTip citizen) throws JAXBException {
        Korisnici users = fetchUsersFromDatabase();
        List<Gradjani> citizens = users.getGradjani();
        Gradjani citizensList = citizens.get(0);

        for(GradjaninTip c: citizensList.getGradjanin()) {
            if(c.getEmail().equals(citizen.getIme())) {       // TODO: ime has to be changed to email!
                return c;
            }
        }
        return null;
    }

    public static Korisnici fetchUsersFromDatabase() throws JAXBException {
        System.out.println("[INFO] Fetching users form database...");

        String inputUsersXmlString = DatabaseAccessor.readXmlFromDatabase(USERS_DOC_ID);

        return  (Korisnici) Converter.unmarshall(UnmarshallType.FROM_STRING, inputUsersXmlString, Korisnici.class);
    }

    public static void addNewUser(GradjaninTip citizen) throws JAXBException {
        Korisnici users = fetchUsersFromDatabase();
        List<Gradjani> citizens = users.getGradjani();
        Gradjani citizensList = citizens.get(0);

        citizensList.getGradjanin().add(citizen);

        writeChangesToDatabase(users);

        System.out.println("Added to database: Name: "+ citizen.getIme() + ", "
                + "Surname: " + citizen.getPrezime() + ", Email: " + citizen.getEmail()
                + "<br/><a>Verify the content at: http://147.91.177.194:8000/v1/documents?database=Tim16&uri=" + USERS_DOC_ID + "</a>");
    }

    public static void updateUser(GradjaninTip citizen) throws JAXBException {
        Korisnici users = fetchUsersFromDatabase();
        List<Gradjani> citizensCollection = users.getGradjani();
        Gradjani citizensList = citizensCollection.get(0);
        List<GradjaninTip> citizens = citizensList.getGradjanin();

        for(int i = 0; i < citizens.size(); i++) {
            if(citizens.get(i).getEmail().equals(citizen.getEmail())) {
                citizensList.getGradjanin().get(i).setIme(citizen.getIme());
                citizensList.getGradjanin().get(i).setPrezime(citizen.getPrezime());
                writeChangesToDatabase(users);
                System.out.println("Updated user with email: " + citizen.getEmail() + " at database records.");
            }
        }
        System.out.println("User with email: " + citizen.getEmail() + " was not found in database records.");
    }

    public static void deleteUser(GradjaninTip citizen) throws JAXBException {
        Korisnici users = fetchUsersFromDatabase();                 // TODO: change model at once! :)
        List<Gradjani> citizensCollection = users.getGradjani();
        Gradjani citizensList = citizensCollection.get(0);
        List<GradjaninTip> citizens = citizensList.getGradjanin();

        for(int i = 0; i < citizens.size(); i++) {
            if(citizens.get(i).getEmail().equals(citizen.getIme())) {       // TODO: ime has to be changed to email!
                citizensList.getGradjanin().remove(i);
                writeChangesToDatabase(users);
                System.out.println("Deleted user with email: " + citizen.getIme() + " from database records.");
            }
        }
        System.out.println("User with email: " + citizen.getIme() + " was not found in database records.");
    }

    /**
     * Overwrites current users document in database records.
     * @param users Object that will be marshalled and written to database.
     * @throws JAXBException
     */
    private static void writeChangesToDatabase(Korisnici users) throws JAXBException {
        System.out.println("[INFO] Writing 'users' changes to database...");

        String outputUsersXmlString = Converter.marshall(MarshallType.TO_STRING, users, "");

        DatabaseAccessor.writeXmlToDatabase(USERS_DOC_ID, outputUsersXmlString);
    }

}
