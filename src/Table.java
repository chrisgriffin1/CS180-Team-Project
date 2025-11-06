public class Table {
    private int tableNumber;
    private User[] peopleSittingHere;
    private int price;

    public Table(int tableNumber, int capacity, int price) {
        this.tableNumber = tableNumber;
        this.peopleSittingHere = new User[capacity];
        this.price = price;
    }
    
    public int getTableNumber() {
        return tableNumber;
    }

    public User[] getPeopleSittingHere() {
        return peopleSittingHere;
    }

    public int getPrice() {
        return price;
    }

}