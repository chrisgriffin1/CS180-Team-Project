import java.io.Serializable;
/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */

 public class User implements Serializable {

    private String userName;
    private String password;

    public User(String userName, String password) {
        if (userName == null || password == null) {
            throw new IllegalArgumentException("Username and password cannot be null.");
        }
        this.userName = userName;
        this.password = password;
    }
    // Gets username
    public String getUserName() {
        return userName;
    }
    // Gets password
    public String getPassword() {
        return password;
    }
}