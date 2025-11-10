import org.junit.Test;
import static org.junit.Assert.*;

public class TableTest{

    @Test(timeout = 1000)
    public void testTableConstructorAndGetters() {
        Table table = new Table(1, 2, 2, 8);

        assertNotNull("Table object should not be null", table);
        assertEquals("Table row should be 1", 1, table.getTableRow());
        assertEquals("Table column should be 2", 2, table.getTableColumn());
        assertEquals("Capacity should be 2", 2, table.getSeats().length);
    }

    @Test(timeout = 1000)
    public void testTableWithZeroCapacity() {
        Table table = new Table(3, 1, 4, 10);

        assertNotNull("Table object should not be null", table);
        assertEquals("Table row should be 3", 3, table.getTableRow());
        assertEquals("Table column should be 1", 1, table.getTableColumn());
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testTableWithNegativeCapacity() {
        Table table = new Table(2, 5, 2, 5);

        assertNotNull(table);
        assertEquals("Table row should be 2", 2, table.getTableRow());
        assertEquals("Table column should be 5", 5, table.getTableColumn());
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testTableWithExcessiveCapacity() {
        Table table = new Table(4, 5, 6, 8);

        assertNotNull(table);
        assertEquals("Table row should be 4", 4, table.getTableRow());
        assertEquals("Table column should be 5", 5, table.getTableColumn());
    }
}