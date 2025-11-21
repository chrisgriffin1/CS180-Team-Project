/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */


public class Restaurant {
    private int capacity;
    private Table[][] seatingPlan;
    private Reservation reservation;

    public Restaurant (Reservation reservation, int capacity, Table[][] seatingPlan) {
        if (reservation == null || capacity <= 0 || seatingPlan.length == 0) {
            throw new IllegalArgumentException("Invalid arguments for Restaurant constructor");
        }
        this.reservation = reservation;
        this.capacity = capacity;
        this.seatingPlan = seatingPlan;
    }

    //getter method which gets reservation
    public Reservation getReservation() {
        return reservation;
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
        int row = r.getTable().getTableRow();
        int column = r.getTable().getTableColumn();

        // check if table is occupied, otherwise fill the table
        if (!(r.isTableOccupied(r.getTable()))) {
            Table selected = seatingPlan[row][column]; 
            Seat[] seats = selected.getSeats();
            for (Seat seat : seats) {
                seat.setUser(r.getUser());
                seat.occupy();   
            }    
        }


    }

    public void removeReservation(Reservation r) {
        int row = r.getTable().getTableRow();
        int column = r.getTable().getTableColumn();
        Table selected = seatingPlan[row][column]; 
        Seat[] seats = selected.getSeats();
        for (Seat seat : seats) {
            seat.setUser(null);
            seat.free();   
        }
    }


}   