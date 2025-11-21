import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */

public class ClientTest {

    @Test(timeout = 5000)
    public void testClientConstructorNotNull() {
        Client c = null;
        try {
            c = new Client();
        } catch (Exception e) {         
            fail("Client constructor threw an exception: " + e.getMessage());
        }
        assertNotNull("Client object should not be null after construction", c);
    }

    @Test(timeout = 5000)
    public void testSetupGUIDoesNotCrash() {
        Client c = null;
        try {
            c = new Client();
            c.setupGUI();            
        } catch (Exception e ) {
            fail("setupGUI method threw an exception: " + e.getMessage());
        }
    }

    @Test(timeout = 5000)
    public void testLoginGUIDoesNotCrash() {
        Client c = null;
        try {
            c = new Client();
            c.loginGUI();            
        } catch (Exception e ) {
            fail("loginGUI method threw an exception: " + e.getMessage());
        }
    }

    @Test(timeout = 5000)
    public void testSendCommandDoesNotCrash() {
        Client c = null;
        String result = null;
        try {
            c = new Client();
            result = c.sendCommand("TEST_COMMAND", "param1", "param2");            
        } catch (Exception e ) {
            fail("sendCommand method threw an exception: " + e.getMessage());
        }
        assertNotNull("sendCommand should return a non-null result", result);
    }

    @Test(timeout = 5000)
    public void testClientMainDoesNotCrash() {
        try {
            Client.main(new String[0]);           
        } catch (Exception e ) {
            fail("Client main method threw an exception: " + e.getMessage());
        }
    }
}