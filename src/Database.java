import java.io.*;
import java.util.ArrayList;

/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */

public class Database implements DatabaseGuide {

    public static Object lock = new Object();
    File reservationsFile = new File("reservations.txt");
    File usersFile = new File("users.txt");

    ArrayList<User> users;
    ArrayList<Reservation> reservations;

    public Database() {
        users = readUsers();
        reservations = readReservations();
    }

    // Creates a new User object given a username and password
    public void makeNewUser(String username, String password) {
        synchronized (lock) {
            User newUser = new User(username, password);
            users.add(newUser);
            saveUsers();
        }
    }

    // Deletes a User object given a username
    public void deleteUser(String username) {
        synchronized (lock) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserName().equals(username)) {
                    users.remove(i);
                    break;
                }
            }
            saveUsers();
        }
    }

    // Creates a new Reservation object
    public void createReservation (String day, double time, User user, int partySize, Table table) {
        synchronized (lock) {
            Reservation reservation = new Reservation(day, time, user, partySize, table);
            reservations.add(reservation);
            saveReservations();
        }
    }

    // Deletes a Reservation
    public void deleteReservation (Reservation reservation) {
        synchronized (lock) {
            reservations.remove(reservation);
            saveReservations();
        }
    }

    /**
     * saves users to file
     */
    public void saveUsers() {
        synchronized (lock) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
                oos.writeObject(this.users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads users from file
     * Includes check to prevent FileNotFoundException on first run
     */
    public ArrayList<User> readUsers() {
        synchronized (lock) {
            if (!usersFile.exists()) {
                return new ArrayList<User>();
            }
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile))) {
                return (ArrayList<User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return new ArrayList<User>();
        }
    }

    /**
     * saves reservations to file
     */
    public void saveReservations() {
        synchronized (lock) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(reservationsFile))) {
                oos.writeObject(this.reservations);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads reservations from file
     * Includes check to prevent FileNotFoundException on first run
     */
    public ArrayList<Reservation> readReservations() {
        synchronized (lock) {
            if (!reservationsFile.exists()) {
                return new ArrayList<Reservation>();
            }
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(reservationsFile))) {
                return (ArrayList<Reservation>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }

    /**
     * Gets users
     */
    public ArrayList<User> getUsers() {
        synchronized (lock) {
            return users;
        }
    }

    /**
     * Gets reservations
     */
    public ArrayList<Reservation> getReservations() {
        synchronized (lock) {
            return reservations;
        }
    }
}