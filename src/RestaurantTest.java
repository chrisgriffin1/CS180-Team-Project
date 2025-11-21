import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 10, 2025
 */
public class RestaurantTest {

    private Restaurant restaurant;
    private User testUser;
    private Table testTable;
    private Reservation testReservation;
    private Table[][] seatingPlan;

    /**
     * Sets up the test environment before each test.
     * This creates all the objects needed to test the Restaurant.
     */
    @Before
    public void setUp() {
        testUser = new User("testUser", "pass");
        
        testTable = new Table(0, 0, 2, 10); 
        
        seatingPlan = new Table[1][1];
        seatingPlan[0][0] = testTable;
        
        testReservation = new Reservation("Monday", 18.00, testUser, 2, testTable);
        
        restaurant = new Restaurant("Tuesday", 20.00, testUser, 2, seatingPlan);
    }

    @Test(timeout = 1000)
    public void testGetters() {
        assertEquals("Capacity should be 2", 2, restaurant.getCapacity());
        assertNotNull("Seating plan should not be null", restaurant.getSeatingPlan());
        assertEquals("Seating plan should contain testTable", testTable, restaurant.getSeatingPlan()[0][0]);
    }

    @Test(timeout = 1000)
    public void testOccupyReservation() {
        assertFalse("Table should be free initially", testReservation.isTableOccupied(testTable));
        
        restaurant.occupyReservation(testReservation);
        
        assertTrue("Table should be occupied after", testReservation.isTableOccupied(testTable));
        
        Seat seat = testTable.getSeats()[0];
        assertEquals("Seat user should be testUser", testUser, seat.getUser());
    }

    @Test(timeout = 1000)
    public void testRemoveReservation() {
        restaurant.occupyReservation(testReservation);
        assertTrue("Table should be occupied", testReservation.isTableOccupied(testTable));
        
        restaurant.removeReservation(testReservation);
        
        assertFalse("Table should be free after removal", testReservation.isTableOccupied(testTable));
        
        Seat seat = testTable.getSeats()[0];
        assertNull("Seat user should be null after removal", seat.getUser());
    }
}