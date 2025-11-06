public class Table {
    private int tableNumber;
    private User[] peopleSittingHere;

    public Table(int tableNumber, int capacity) {
        this.tableNumber = tableNumber;
        this.peopleSittingHere = new User[capacity];
    }
    
    public int getTableNumber() {
        return tableNumber;
    }

    public User[] getPeopleSittingHere() {
        return peopleSittingHere;
    }

}