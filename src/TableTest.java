import org.junit.Test;
import static org.junit.Assert.*;

public class TableTest{

    @Test(timeout = 1000)
    public void testTableConstructorAndGetters() {
        Table table = new Table(1, 2);

        assertNotNull("Table object should not be null", table);
        assertEquals("Table number should be 1", 1, table.getTableNumber());
        assertEquals("Capacity should be 2", 2, table.getCapacity());
    }

    @Test(timeout = 1000)
    public void testTableWithZeroCapacity() {
        Table table = new Table(3, 0);

        assertNotNull("Table object should not be null", table);
        assertEquals("Capacity should be 0", 0, table.getCapacity());
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testTableWithNegativeCapacity() {
        Table table = new Table(2, -2);

        assertNotNull(table);
        assertEquals("Capacity should be -2", -2, table.getCapacity());
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testTableWithExcessiveCapacity() {
        Table table = new Table(4, 5);

        assertNotNull(table);
        assertEquals("Capacity should be 5", 5, table.getCapacity());
    }
}