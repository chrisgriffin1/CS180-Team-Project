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
        File testFile = new File("users.txt");
        if (testFile.exists()) {
            testFile.delete();
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
        assertEquals("Number of users read should match number saved", usersToSave.length, usersRead.size());

        assertEquals("First user's username should match", db.getUsers().get(0).getUserName(), usersRead.get(0).getUserName());
        assertEquals("First user's password should match", db.getUsers().get(0).getPassword(), usersRead.get(0).getPassword());

        assertEquals("Second user's username should match", db.getUsers().get(1).getUserName(), usersRead.get(1).getUserName());
        assertEquals("Second user's password should match", db.getUsers().get(1).getPassword(), usersRead.get(1).getPassword());
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
}
