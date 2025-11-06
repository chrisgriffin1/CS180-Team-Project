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
    public void readUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile))) {
            User[] users = (User[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    

}