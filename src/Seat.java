public class Seat {
    private User user;
    private boolean isOccupied;

    public Seat(User user) {
        this.user = user;
        this.isOccupied = false;
    }

    public User getUser() {
        return user;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public void occupy() {
        isOccupied = true;
    }

    public void occupySeat(Seat seat) {
        for (int a = 0; a < seats.length; a++) {
            seats[a] = seat;
        }
    }
}
