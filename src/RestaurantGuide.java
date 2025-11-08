public interface RestaurantGuide {
    public int getCapacity();
    public Table[][] getSeatingPlan();
    public void occupyReservation(Reservation r);
    public void removeReservation(Reservation r);
}
