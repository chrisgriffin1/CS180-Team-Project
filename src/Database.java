import java.io.*;

// TODO: need to make it thread safe



public class Database {
    public static Object lock = new Object();
    File reservationsFile = new File("reservations.txt");
    File usersFile = new File("users.txt");

    public void makeNewUser() {

    }

    public void deleteUser() {


    }

    public void createReservation (String day, double time, User user, int partySize, Table table) {
        Reservation reservation = new Reservation(day, time, user, partySize, table);
        reservation.occupyReservation(table);
    }

    public void deleteReservation () {


    }


    public void saveUsers(User[] users) {
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
                return (User[]) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new User[0];
        }
    }

    public void saveReservations(Reservation[] reservations) {
        synchronized (lock) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(reservationsFile))) {
                oos.writeObject(reservations);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public Reservation[] readReservations() {
        synchronized (lock) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(reservationsFile))) {
                return (Reservation[]) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return new Reservation[0];
        } 
    }

}    