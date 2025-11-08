import java.util.ArrayList;

public class Restaurant {
    private Reservation reservation;
    private int capacity;
    private Table[][] seatingPlan;


    public Restaurant (Reservation reservation, int capacity, Table[][] seatingPlan) {
        this.Reservation = reservation;
        this.capacity = capacity;
        this.seatingPlan = seatingPlan;
    }

    public int getCapacity() { 
        return capacity; 
    }

    public Table[][] getSeatingPlan() {
        return seatingPlan;
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
