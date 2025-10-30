public class Reservation {
    private String day;
    private double time;
    private User user;

    public Reservation (String day, double time, User user) {
        this.day = day;
        this.time = time;
        this.user = user;
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


}