public class Table implements TableGuide {
    private int tableRow;
    private int tableColumn;
    private Seat[] seats;
    private int price;

    public Table(int tableNumber, int capacity) {
        if (capacity < 0 || capacity > 2) {
            throw new IllegalArgumentException("Capacity must be between 0 and 2");
        }
        this.tableNumber = tableNumber;
        this.peopleSittingHere = new User[capacity];
    }
    
    public int getTableRow() {
        return tableRow;
    }

    public int getTableColumn() {
        return tableColumn;
    }

    public Seat[] getSeats() {
        return seats;
    }

    public int getPrice() {
        return price;
    }

    /*
    public void occupySeats(Seat seat) {
        for (int a = 0; a < seats.length; a++) {
            seats[a] = seat;
        }
    }
    */
}