import org.junit.Test;
import static org.junit.Assert.*;

/*
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */
public class SeatTest {

    @Test(timeout = 1000)
    public void testSeatConstructorAndGetters() {
        User testUser = new User("testUser", "pass");
        Seat seat = new Seat(testUser);

        assertNotNull("Seat object should not be null", seat);
        assertEquals("User should be testUser", testUser, seat.getUser());
        assertFalse("Seat should be not occupied by default", seat.getIsOccupied());
    }

    @Test(timeout = 1000)
    public void testOccupyAndFree() {
        User testUser = new User("testUser", "pass");
        Seat seat = new Seat(testUser);

        assertFalse("Seat should start not occupied", seat.getIsOccupied());

        seat.occupy();
        assertTrue("Seat should be occupied after occupy()", seat.getIsOccupied());

        seat.free();
        assertFalse("Seat should be free after free()", seat.getIsOccupied());
    }

    @Test(timeout = 1000)
    public void testSetUser() {
        User originalUser = new User("original", "pass1");
        Seat seat = new Seat(originalUser);

        assertEquals("User should be originalUser", originalUser, seat.getUser());

        User newUser = new User("newUser", "pass2");
        seat.setUser(newUser);

        assertEquals("User should be updated to newUser", newUser, seat.getUser());
    }
}