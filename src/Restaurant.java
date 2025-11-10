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

    public String getDay() {
        return day;
    }

    public double getTime() {
        return time;
    }

    public User getUser() {
        return user;
    }

    public int getPartySize() {
        return partySize;
    }
}
