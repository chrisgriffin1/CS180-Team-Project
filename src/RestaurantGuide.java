/*
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */

public interface RestaurantGuide {
    public int getCapacity();
    public Table[][] getSeatingPlan();
    public void occupyReservation(Reservation r);
    public void removeReservation(Reservation r);
}
