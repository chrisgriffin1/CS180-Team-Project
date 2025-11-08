public interface ReservationGuide {
    boolean isTableOccupied(Table table);
    public String getDay();
    double getTime();
    User getUser();
    Table getTable();
    int getPartySize();
}