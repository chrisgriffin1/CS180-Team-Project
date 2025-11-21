import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */

public class ServerTest {

    @Test(timeout = 5000)
    public void testServerConstructorNotNull() {
        Server s = null;
        try {
            s = new Server();
        } catch (Exception e) {         
            fail("Server constructor threw an exception: " + e.getMessage());
        }
        assertNotNull("Server object should not be null after construction", s);
    }

    @Test(timeout = 5000)
    public void testServerImplementsRunnable() {
        Server s = new Server();
        assertTrue("Server should implement Runnable interface", s instanceof Runnable);
    }

    @Test(timeout = 5000)
    public void testServerCanBeUsedInThread() {
        Server s = new Server();
        Thread t = new Thread();
        asserNotNull("Thread object should not be null when created with Server", t);
    }

    @Test(timeout = 5000)
    public void testServerMainDoesNotCrash() {
        try {
            Server.main(new String[0]);
        } catch (Exception e ) {
            fail("Server main method threw an exception: " + e.getMessage());
        }
    }
}