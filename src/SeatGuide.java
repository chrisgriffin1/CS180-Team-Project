/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */


public interface SeatGuide {
    public User getUser();
    public void setUser(User user);
    public boolean getIsOccupied();
    public void occupy();
    public void free();
}
