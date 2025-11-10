/*
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */

public interface ReservationGuide {
    public String getDay();
    double getTime();
    User getUser();
    Table getTable();
    int getPartySize();
    boolean isTableOccupied(Table table);
}