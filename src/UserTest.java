import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    
    @Test(timeout = 1000)
    public void testUserConstructorandGetters() {
        User user = new User("testUser", "password123");

        assertNotNull("User object should not be null", user);
        assertEquals("Username should be 'testUser'", "testUser", user.getUserName());
        assertEquals("Password should be 'password123'", "password123", user.getPassword());
    }

    @Test(timeout = 1000)
    public void testUserEmptyConstructor() {
        User user = new User("", "");

        assertNotNull(user);
        assertNull("Username should not be an empty string", "", user.getUsername());
        assertNull("Password should not be an empty string", "", user.getPassword());
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testUserWithNullValues() {
        User nullUser = new User(null, null);

        assertNotNull(nullUser);
        assertNull("Username should not be null", user.getUsername());
        assertNull("Password should not be null", user.getPassword());
    }
}