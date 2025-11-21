public class Restaurant implements RestaurantGuide {
    private Reservation reservation;
    private int capacity;
    private Table[][] seatingPlan;


    public Restaurant (Reservation reservation, int capacity, Table[][] seatingPlan) {
        this.reservation = reservation;
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

        if ((r.isTableOccupied(r.getTable()))) {
            Table selected = seatingPlan[row][column]; 
            
            Seat[] seats = selected.getSeats();
            for (Seat seat : seats) {
                seat.setUser(null);
                seat.free();   
            }
        }    
    }

}
