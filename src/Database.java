import java.io.*;
import java.util.ArrayList;

public class Database implements IDatabase {

    public static Object lock = new Object();
    File reservationsFile = new File("reservations.txt");
    File usersFile = new File("users.txt");
    private User[] lastReadUsers;

    ArrayList<User> users;
    ArrayList<Reservation> reservations;

    public Database() {
        users = readUsers();
        reservations = readReservations();
    }

    public void makeNewUser(String username, String password) {
        synchronized (lock) {
            User newUser = new User(username, password);
            users.add(newUser);
            saveUsers();
        /**try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
            oos.writeObject(users);
        }   catch (IOException e) {
            e.printStackTrace();
        }
        */
        }
    }

    public void deleteUser(String username) {
        synchronized (lock) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserName().equals(username)) {
                    users.remove(i);
                    break;
                }
            }
            saveUsers();
            /**
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
                oos.writeObject(users);
            } catch (IOException e) {
                e.printStackTrace();
            }   
            */
        }
    }

    public void createReservation (String day, double time, User user, int partySize, Table table) {
        synchronized (lock) {
            Reservation reservation = new Reservation(day, time, user, partySize, table);
            reservations.add(reservation);
            saveReservations();
        }
    }

    public void deleteReservation (Reservation reservation) {
        synchronized (lock) {
            reservations.remove(reservation);
            saveReservations();
        
        }
        
    }


    public void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
            oos.writeObject(this.users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<User> readUsers() {
        synchronized (lock) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile))) {
                return (ArrayList<User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }
    
    public void saveReservations() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(reservationsFile))) {
            oos.writeObject(this.reservations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Reservation> readReservations() {
        synchronized (lock) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(reservationsFile))) {
                return (ArrayList<Reservation>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }

    public ArrayList<User> getUsers() {
        synchronized (lock) {
            return users;
        }
    }

    public ArrayList<Reservation> getReservations() {
        synchronized (lock) {
            return reservations;
        }
    }
   
    public User[] getLastReadUsers() {
        return lastReadUsers;
    }
}