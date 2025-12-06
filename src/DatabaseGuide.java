import java.util.ArrayList;

/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */

public interface DatabaseGuide {
    void run();

    void makeNewUser(String username, String password);

    void deleteUser(String username);

    void createReservation(String day, double time, User user, int partySize, Table table);

    void deleteReservation(Reservation reservation);

    void saveUsers();

    ArrayList<User> readUsers();

    void saveReservations();

    ArrayList<Reservation> readReservations();

    ArrayList<User> getUsers();

    ArrayList<Reservation> getReservations();
}