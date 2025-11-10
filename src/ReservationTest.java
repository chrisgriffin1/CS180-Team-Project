import org.junit.Test;
import static org.junit.Assert.*;

public class ReservationTest {

    @Test(timeout = 1000)
    public void testReservationConstructorAndGetters() {
        User testUser = new User("testUser", "password123");
        Table newTable1 = new Table(4, 4, 1, 4);
        Reservation reservation = new Reservation("Friday", 19.30, testUser, 4, newTable1);

        assertNotNull("Reservation object should not be null", reservation);
        assertEquals("Day should be 'Friday'", "Friday", reservation.getDay());
        assertEquals("Time should be 19.30", 19.30, reservation.getTime(), 0.001);
        assertEquals("User should be the testUser", testUser, reservation.getUser());
        assertEquals("Party size should be 4", 4, reservation.getPartySize());
    }

    @Test(timeout = 1000)
    public void testReservationWithInvalidPartySize() {
        User testUser = new User("guest", "guestpass");
        Table newTable2 = new Table(2, 5, 2, 3);
        Reservation reservation = new Reservation("Saturday", 20.00, testUser, -1, newTable2);

        assertNotNull("Reservation should still be created", reservation);
        assertEquals("Party size should be -1", -1, reservation.getPartySize());
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testReservationWithNullUser() {
        Table newTable3 = new Table(3, 4, 2 ,3);
        Reservation reservation = new Reservation("Sunday", 18.00, null, 2, newTable3);

        assertNotNull(reservation);
        assertNull(reservation.getUser());
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testReservationWithNullDay() {
        User testUser = new User("alice", "alicepass");
        
        Reservation reservation = new Reservation(null, 17.00, testUser, 3, new Table(1, 2, 2, 1));

        assertNotNull(reservation);
        assertNull(reservation.getDay());
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testReservationWithZeroPartySize() {
        User testUser = new User("bob", "bobpass");
        Reservation reservation = new Reservation("Monday", 21.00, testUser, 0, new Table(1, 3, 2, 3));
        assertNotNull(reservation);
        assertEquals("Party size should be 0", 0, reservation.getPartySize());
    }
}