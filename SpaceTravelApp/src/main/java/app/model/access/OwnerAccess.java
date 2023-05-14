package app.model.access;

import app.model.users.Owner;
import app.model.users.User;

import java.util.List;

public class OwnerAccess implements Accessible {

    @Override
    public int signup(String username, int birthYear, int password, List<User> existingUsers, int token) {
        Owner.setAccessToken(9999);

        for (User user : existingUsers) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username " + username + " is already being used!");
                return 1; // Incorrect Username Code
            }
        }
        if (token != Owner.getAccessToken()) {
            System.out.println("Incorrect access token!");
            return 3; // Incorrect Access Token Code
        }
        existingUsers.add(new Owner(username, birthYear, password));
        System.out.println("Signed in as Owner!");
        return 0;
    }

    @Override
    public int login(String username, int password, List<User> existingUsers, int token) {
        Owner.setAccessToken(9999);
        for (User user : existingUsers) {
            if (user.getUsername().equals(username)) {
                if (user.getClass() != Owner.class) {
                    return 1;
                }
                if (user.getPassword() == password) {
                    if (token != Owner.getAccessToken()) {
                        System.out.println("Incorrect access token!");
                        return 3; // Incorrect Access Token Code
                    }
                    System.out.println("Logged in as Owner!");
                    return 0; // Success Code
                } else {
                    System.out.println("Incorrect password. Try again!" + user.getPassword() + "|" + password);
                    return 2; // Incorrect Password Code
                }
            }
        }

        System.out.println("User with name " + username + " not found.");
        return 1; // Incorrect Username Code
    }
}
