public class Table {
    private int tableNumber;
    private Seat[] seats;
    private int price;

    public Table(int tableNumber, int capacity, int price) {
        this.tableNumber = tableNumber;
        this.seats = new Seat[capacity];
        this.price = price;
    }
    
    public int getTableNumber() {
        return tableNumber;
    }

    public Seat[] getSeats() {
        return seats;
    }

    public int getPrice() {
        return price;
    }

    public void occupySeat(Seat seat) {
        for (int a = 0; a < seats.length; a++) {
            seats[a] = seat;
        }
    }

}