public class Seat implements SeatGuide {
    private User user;
    private boolean isOccupied;

    public Seat(User user) {
        this.user = user;
        this.isOccupied = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public void occupy() {
        isOccupied = true;
    }

    public void free() {
        isOccupied = false;
    }
}
