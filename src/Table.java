public class Table {
    private int tableRow;
    private int tableColumn;
    private Seat[] seats;
    private int price;

    public Table(int tableRow, int tableColumn, int capacity, int price) {
        this.tableRow = tableRow;
        this.tableColumn = tableColumn;
        this.seats = new Seat[capacity];
        this.price = price;
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

    public void occupyTable(Table table) {
        for (int a = 0; a < seats.length; a++) {
            seats[a] = seat;
        }
    }

}