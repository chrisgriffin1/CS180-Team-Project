public class Reservation {
    private String day;
    private double time;
    private User user;
    private int partySize;

    public Reservation (String day, double time, User user, int partySize) {
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