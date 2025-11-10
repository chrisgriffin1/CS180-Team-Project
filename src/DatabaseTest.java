import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;    
import org.junit.After;
import java.io.File;

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

        User[] usersRead = db.getLastReadUsers();

        assertNotNull("Users read from database should not be null", usersRead);
        assertEquals("Number of users read should match number saved", usersToSave.length, usersRead.length);

        assertEquals("First user's username should match", usersToSave[0].getUserName(), usersRead[0].getUserName());
        assertEquals("First user's password should match", usersToSave[0].getPassword(), usersRead[0].getPassword());

        assertEquals("Second user's username should match", usersToSave[1].getUserName(), usersRead[1].getUserName());
        assertEquals("Second user's password should match", usersToSave[1].getPassword(), usersRead[1].getPassword());
    }

    @Test(timeout = 1000)
    public void testReadUsersWhenFileMissing() {
        Database db = new Database();
        File testFile = new File("users.txt");
        if (testFile.exists()) {
            testFile.delete();
        }
        db.readUsers();
        assertNull("No users should be read when file is missing", db.getLastReadUsers());
    }
}
