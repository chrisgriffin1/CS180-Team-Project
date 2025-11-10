/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */

public class Reservation implements ReservationGuide {
    private String day;
    private double time;
    private Table table;
    private int partySize;
    private User user;

    public Reservation (String day, double time, User user, int partySize, Table table) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        if (day == null) {
            throw new IllegalArgumentException("day cannot be null");
        }
       
        if (partySize == 0) {
            throw new IllegalArgumentException("partySize cannot be zero");
        }

        this.day = day;
        this.time = time;
        this.table = table;
        this.partySize = partySize;
        this.user = user;
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

    //getter method which gets table
    public Table getTable() {
        return table;
    }

    //getter method which gets party size
    public int getPartySize() {
        return partySize;
    }

    // method below checks to see if table given as input is occupied - returns true if occupied and false if not occupied 
    public boolean isTableOccupied(Table table) {
        for (int a = 0; a < table.getSeats().length; a++) {
            if ((table.getSeats()[a].getIsOccupied()) == true) {
                return true;
            } 
        }
        return false;
    }

}