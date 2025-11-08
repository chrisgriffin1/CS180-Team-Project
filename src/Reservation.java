import java.util.ArrayList;

public class Reservation {
    private String day;
    private double time;
    private Table table;
    private int partySize;

    public Reservation (String day, double time, User user, int partySize, Table table) {
        this.day = day;
        this.time = time;
        this.table = table;
        this.partySize = partySize;
    }

    public String getDay() {
        return day;
    }

    public double getTime() {
        return time;
    }

    public User getTable() {
        return table;
    }

    public int getPartySize() {
        return partySize;
    }

    /**
     * occupy 2D array with the reservation 
     */
    public void occupyReservation(Table reservation) {
        for (int i = 0; i < seatingPlan.length; i++) {
            for (int j = 0; j < seatingPlan[i].length; j++) {
                seatingPlan[i][j] = reservation;
            }
        }
    }

}