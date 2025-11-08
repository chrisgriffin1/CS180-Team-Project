import java.io.*;
import java.util.ArrayList;
// TODO: need to make it thread safe



public class Database {
    public static Object lock = new Object();
    File reservationsFile = new File("reservations.txt");
    File usersFile = new File("users.txt");

    ArrayList<User> users;
    ArrayList<Reservation> reservations;

    public void makeNewUser(String username, String password) {
        User newUser = new User(username, password);
        users.add(newUser);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
            oos.writeObject(users);
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.remove(i);
                break;
            }
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }

    public void createReservation (String day, double time, User user, int partySize, Table table) {
        Reservation reservation = new Reservation(day, time, user, partySize, table);
        reservation.occupyReservation(table);
    }

    public void deleteReservation (Reservation reservation) {
        reservations.remove(reservation);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(reservationsFile))) {
            oos.writeObject(reservations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveUsers(ArrayList<User> users) {
        synchronized (lock) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
                oos.writeObject(users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public User[] readUsers() {
        synchronized (lock) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile))) {
                return (ArrayList<User>) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }
    
    public void saveReservations(ArrayList<Reservation> reservations) {
        synchronized (lock) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(reservationsFile))) {
                oos.writeObject(reservations);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

}    