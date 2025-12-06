import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */

public class DatabaseTest {
    @org.junit.After
    @org.junit.Before
    public void cleanDatabaseFile() {
        File usersFile = new File("users.txt");
        if (usersFile.exists()) {
            usersFile.delete();
        }
        File resFile = new File("reservations.txt");
        if (resFile.exists()) {
            resFile.delete();
        }
    }

    @Test(timeout = 1000)
    public void testSaveAndReadUsers() {
        Database db = new Database();
        User[] usersToSave = new User[2];
        db.makeNewUser("john", "password123");
        db.makeNewUser("jane", "secure456");

        db.saveUsers();
        db.readUsers();

        ArrayList<User> usersRead = db.getUsers();

        assertNotNull("Users read from database should not be null", usersRead);
        assertEquals("Number of users read should match number saved", 2, usersRead.size());

        assertEquals("First user's username should match", "john", usersRead.get(0).getUserName());
        assertEquals("Second user's username should match", "jane", usersRead.get(1).getUserName());
    }

    @Test(timeout = 1000)
    public void testReadUsersWhenFileMissing() {
        Database db = new Database();
        File testFile = new File("users.txt");
        if (testFile.exists()) {
            testFile.delete();
        }
        db.readUsers();
        assertEquals(true, db.readUsers().size() == 0);
    }

    @Test(timeout = 1000)
    public void testDeleteUser() {
        Database db = new Database();
        db.makeNewUser("user1", "pass1");
        db.makeNewUser("user2", "pass2");

        assertEquals(2, db.getUsers().size());

        db.deleteUser("user1");
        assertEquals(1, db.getUsers().size());
        assertEquals("user2", db.getUsers().get(0).getUserName());
    }

    @Test(timeout = 1000)
    public void testCreateAndReadReservations() {
        Database db = new Database();
        User u = new User("resUser", "pass");
        Table t = new Table(1, 1, 2, 0);

        db.createReservation("Monday", 18.0, u, 2, t);

        ArrayList<Reservation> resList = db.getReservations();
        assertEquals(1, resList.size());
        assertEquals("Monday", resList.get(0).getDay());
        assertEquals(18.0, resList.get(0).getTime(), 0.001);
    }

    @Test(timeout = 1000)
    public void testDeleteReservation() {
        Database db = new Database();
        User u = new User("resUser", "pass");
        Table t = new Table(1, 1, 2, 0);

        db.createReservation("Tuesday", 19.0, u, 2, t);
        assertEquals(1, db.getReservations().size());

        // Create a reservation object to match for deletion
        // Note: deleteReservation matches by value (day, time, table)
        Reservation rToDelete = new Reservation("Tuesday", 19.0, u, 2, t);

        db.deleteReservation(rToDelete);
        assertEquals(0, db.getReservations().size());
    }
}
