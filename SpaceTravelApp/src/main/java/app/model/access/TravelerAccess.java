package app.model.access;

import app.model.users.Traveler;
import app.model.users.User;
import java.util.List;

public class TravelerAccess implements Accessible {

    @Override
    public boolean signup(String username, int birthYear, int password, List<User> existingUsers) {
        for (User user : existingUsers) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username " + username + " is already being used!");
                return false;
            }
        }
        existingUsers.add(new Traveler(username, birthYear, password));
        System.out.println("Signed in as Traveler!");
        return true;
    }

    @Override
    public boolean login(String username, int password, List<User> existingUsers) {
        for (User user : existingUsers) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword() == password) {
                    System.out.println("Logged in as Traveler!");
                    return true;
                } else {
                    System.out.println("Incorrect password. Try again!" + user.getPassword() + "|" + password);
                    return false;
                }
            }
        }

        System.out.println("User with name " + username + " not found.");
        return false;
    }
}
