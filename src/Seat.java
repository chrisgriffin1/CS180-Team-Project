/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */

public class Seat implements SeatGuide {
    private User user;
    private boolean isOccupied;

    public Seat(User user) {
        this.user = user;
        this.isOccupied = false;
    }

    //getter method which gets user
    public User getUser() {
        return user;
    }

    //getter method which gets user
    public void setUser(User user) {
        this.user = user;
    }
    
    //getter method which gets isOccupied
    public boolean getIsOccupied() {
        return isOccupied;
    }

    //method which sets isOccupied to true
    public void occupy() {
        isOccupied = true;
    }

    //method which sets isOccupied to false
    public void free() {
        isOccupied = false;
    }
}
