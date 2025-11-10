public class Reservation implements ReservationGuide {
    private String day;
    private double time;
    private Table table;
    private int partySize;
    private User user;

    public Reservation (String day, double time, User user, int partySize, Table table) {
        this.day = day;
        this.time = time;
        this.table = table;
        this.partySize = partySize;
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

    public Table getTable() {
        return table;
    }

    public int getPartySize() {
        return partySize;
    }

    public boolean isTableOccupied(Table table) {
        for (int a = 0; a < table.getSeats().length; a++) {
            if ((table.getSeats()[a].getIsOccupied()) == true) {
                return true;
            } 
        }
        return false;
    }

}