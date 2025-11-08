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

    public void occupyReservation(Reservation reservation) {
        for (int i = 0; i < ; i++)

    }
    

}
