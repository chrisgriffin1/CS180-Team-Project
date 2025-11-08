import java.util.ArrayList;

public interface IDatabase {
    void makeNewUser(String username, String password);

    void deleteUser(String username);
    
    void createReservation (String day, double time, User user, int partySize, Table table);
    
    void deleteReservation (Reservation reservation);

    ArrayList<User> readUsers();

    ArrayList<User> getUsers();

    ArrayList<Reservation> getReservations();
}