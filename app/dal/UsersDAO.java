package dal;

import converter.Converter;
import converter.types.MarshallType;
import converter.types.UnmarshallType;
import database.DatabaseAccessor;
import models.rs.gov.parlament.korisnici.Korisnici;
import models.rs.gov.parlament.korisnici.Korisnik;

import javax.xml.bind.JAXBException;

/**
 * Created by aleksandar on 9.6.16..
 */
public class UsersDAO {

    private static final String USERS_DOC_ID = "/parliament/users.xml";

    /**
     * Returns citizen object of type {@link Korisnik} from database. If it is not found, returns null.
     * @param user User that contains search criteria.
     * @return  Found user object, or null if it is not found in database.
     * @throws JAXBException
     */
    public static Korisnik getCitizenFromDatabase(Korisnik user) throws JAXBException {
        Korisnici users = fetchUsersFromDatabase();

        for(Korisnik u: users.getKorisnik()) {
            if(u.getEmail().equals(user.getEmail())) {       // TODO: ime has to be changed to email!
                return u;
            }
        }
        return null;
    }

    public static Korisnici fetchUsersFromDatabase() throws JAXBException {
        System.out.println("[INFO] Fetching users form database...");

        String inputUsersXmlString = DatabaseAccessor.readXmlFromDatabase(USERS_DOC_ID);

        return  (Korisnici) Converter.unmarshall(UnmarshallType.FROM_STRING, inputUsersXmlString, Korisnici.class);
    }

    public static void addNewUser(Korisnik user) throws JAXBException {
        Korisnici users = fetchUsersFromDatabase();
        users.getKorisnik().add(user);              // TODO: Change in schema KorisniciWrapper -> Korisnici

        writeChangesToDatabase(users);

        System.out.println("Added to database: Name: "+ user.getIme() + ", "
                + "Surname: " + user.getPrezime() + ", Email: " + user.getEmail()
                + "<br/><a>Verify the content at: http://147.91.177.194:8000/v1/documents?database=Tim16&uri=" + USERS_DOC_ID + "</a>");
    }

    public static void updateUser(Korisnik user) throws JAXBException {
        Korisnici users = fetchUsersFromDatabase();

        for(int i = 0; i < users.getKorisnik().size(); i++) {
            if(users.getKorisnik().get(i).getEmail().equals(user.getEmail())) {
                users.getKorisnik().get(i).setIme(user.getIme());
                users.getKorisnik().get(i).setPrezime(user.getPrezime());
                writeChangesToDatabase(users);
                System.out.println("Updated user with email: " + user.getEmail() + " at database records.");
            }
        }
        System.out.println("User with email: " + user.getEmail() + " was not found in database records.");
    }

    public static void deleteUser(Korisnik user) throws JAXBException {
        Korisnici users = fetchUsersFromDatabase();                 // TODO: change model at once! :)

        for(int i = 0; i < users.getKorisnik().size(); i++) {
            if(users.getKorisnik().get(i).getEmail().equals(user.getIme())) {       // TODO: ime has to be changed to email!
                users.getKorisnik().remove(i);
                writeChangesToDatabase(users);
                System.out.println("Deleted user with email: " + user.getIme() + " from database records.");
            }
        }
        System.out.println("User with email: " + user.getIme() + " was not found in database records.");
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
