import org.junit.Test;
import static org.junit.Assert.*;

/*
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */


public class TableTest{

    @Test(timeout = 1000)
    public void testTableConstructorAndGetters() {
        Table table = new Table(1, 2, 2, 8);

        assertNotNull("Table object should not be null", table);
        assertEquals("Table row should be 1", 1, table.getTableRow());
        assertEquals("Table column should be 2", 2, table.getTableColumn());
        assertEquals("Table capacity should be 2", 2, table.getSeats().length);
        assertEquals("Table price should be 8", 8, table.getPrice());
    }

    @Test(timeout = 1000)
    public void testTableWithZeroCapacity() {
        Table table = new Table(3, 1, 0, 10);

        assertNotNull("Table object should not be null", table);
        assertEquals("Table row should be 3", 3, table.getTableRow());
        assertEquals("Table column should be 1", 1, table.getTableColumn());
        assertEquals("Table capacity should be 0", 0, table.getSeats().length);
        assertEquals("Table price should be 10", 10, table.getPrice());
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testTableWithNegativeCapacity() {
        Table table = new Table(2, 5, -2, 5);

        assertNotNull(table);
        assertEquals("Table row should be 2", 2, table.getTableRow());
        assertEquals("Table column should be 5", 5, table.getTableColumn());
        assertEquals("Table capacity should be 2", 2, table.getSeats().length);
        assertEquals("Table price should be 5", 5, table.getPrice());

    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testTableWithExcessiveCapacity() {
        Table table = new Table(4, 5, 6, 8);

        assertNotNull(table);
        assertEquals("Table row should be 4", 4, table.getTableRow());
        assertEquals("Table column should be 5", 5, table.getTableColumn());
        assertEquals("Table capacity should be 6", 6, table.getSeats().length);
        assertEquals("Table price should be 8", 8, table.getPrice());
    }
}