import java.util.ArrayList;

public class Reservation {
    private String day;
    private double time;
    private Table table;
    private ArrayList<Integer> partySize;

    public Reservation (String day, double time, User user, ArrayList<Integer> partySize, Table table) {
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

}