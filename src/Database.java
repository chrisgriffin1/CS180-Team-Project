import java.io.*;
public class Database {
    File reservationsFile = new File("reservations.txt");
    File usersFile = new File("users.txt");

    public void saveUsers(User[] users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public User[] readUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile))) {
            return (User[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new User[0];
    }

    public void saveReservations(Reservation[] reservations) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(reservationsFile))) {
            oos.writeObject(reservations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Reservation[] readReservations() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(reservationsFile))) {
            return (Reservation[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Reservation[0];
    }

    

}