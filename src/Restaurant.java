/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */


public class Restaurant {
    private String day;
    private double time;
    private User user;
    private int partySize;
    private int capacity;
    private Table[][] seatingPlan;

    public Restaurant (String day, double time, User user, int partySize) {
        if (day == null || user == null || partySize <= 0) {
            throw new IllegalArgumentException("Invalid arguments for Restaurant constructor");
        }
        this.day = day;
        this.time = time;
        this.user = user;
        this.partySize = partySize;
    }

    //getter method which gets capacity of restaurant
    public int getCapacity() {
        return capacity;
    }

    //getter method for the seating plan of the restaurant
    public Table[][] getSeatingPlan() {
        return seatingPlan;
    }

    public void occupyReservation(Reservation r) {
        


    }

    public void removeReservation(Reservation r) {
        
    }   
    
    

}
