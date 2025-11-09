import org.junit.Test;
import static org.junit.Assert.*;

public class ReservationTest {

    @Test(timeout = 1000)
    public void testReservationConstructorAndGetters() {
        User testUser = new User("testUser", "password123");
        Reservation reservation = new Reservation("Friday", 19.30, testUser, 4);

        assertNotNull("Reservation object should not be null", reservation);
        assertEquals("Day should be 'Friday'", "Friday", reservation.getDay());
        assertEquals("Time should be 19.30", 19.30, reservation.getTime(), 0.001);
        assertEquals("User should be the testUser", testUser, reservation.getUser());
        assertEquals("Party size should be 4", 4, reservation.getPartySize());
    }

    @Test(timeout = 1000)
    public void testReservationWithInvalidPartySize() {
        User testUser = new User("guest", "guestpass");
        Reservation reservation = new Reservation("Saturday", 20.00, testUser, -1);

        assertNotNull("Reservation should still be created", reservation);
        assertEquals("Party size should be -1", -1, reservation.getPartySize());
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testReservationWithNullUser() {
        Reservation reservation = new Reservation("Sunday", 18.00, null, 2);

        assertNotNull(reservation);
        assertNull(reservation.getUser());
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testReservationWithNullDay() {
        User testUser = new User("alice", "alicepass");
        Reservation reservation = new Reservation(null, 17.00, testUser, 3);

        assertNotNull(reservation);
        assertNull(reservation.getDay());
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testReservationWithZeroPartySize() {
        User testUser = new User("bob", "bobpass");
        Reservation reservation = new Reservation("Monday", 21.00, testUser, 0);

        assertNotNull(reservation);
        assertEquals("Party size should be 0", 0, reservation.getPartySize());
    }
}