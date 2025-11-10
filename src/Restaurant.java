/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */


public class Restaurant {
    private String day;
    private double time;
    private User user;
    private int partySize;

    public Restaurant (String day, double time, User user, int partySize) {
        if (day == null || user == null || partySize <= 0) {
            throw new IllegalArgumentException("Invalid arguments for Restaurant constructor");
        }
        this.day = day;
        this.time = time;
        this.user = user;
        this.partySize = partySize;
    }

    //getter method which gets day
    public String getDay() {
        return day;
    }

    //getter method which gets time
    public double getTime() {
        return time;
    }

    //getter method which gets user
    public User getUser() {
        return user;
    }

    //getter method which gets party size
    public int getPartySize() {
        return partySize;
    }
}
