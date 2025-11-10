public interface ReservationGuide {
    public String getDay();
    double getTime();
    User getUser();
    Table getTable();
    int getPartySize();
    boolean isTableOccupied(Table table);
}