import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */

public class ServerTest {

    @Test(timeout = 1000)
    public void testServerConstructorNotNull() {
        Server s = null;
        try {
            s = new Server();
        } catch (Exception e) {
            fail("Server constructor threw an exception: " + e.getMessage());
        }
        assertNotNull("Server object should not be null after construction", s);
    }

    // We cannot test main() easily because it enters an infinite loop in
    // Database.run()
    // and blocks the test thread.
    // However, we can assert that the class exists and can be instantiated.
}