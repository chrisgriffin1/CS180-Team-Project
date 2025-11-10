/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */

public class Table implements TableGuide {
    private int tableRow;
    private int tableColumn;
    private Seat[] seats;
    private int price;

    public Table(int tableRow, int tableColumn, int capacity, int price) {
        if (capacity < 0 || capacity > 2) {
            throw new IllegalArgumentException("Capacity must be between 0 and 2");
        }
        this.tableRow = tableRow;
        this.tableColumn = tableColumn;
        this.seats = new Seat[capacity];
        this.price = price; 
    }
    // Gets table row
    public int getTableRow() {
        return tableRow;
    }

    // Gets table column
    public int getTableColumn() {
        return tableColumn;
    }

    // Gets seats
    public Seat[] getSeats() {
        return seats;
    }

    // Gets price
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