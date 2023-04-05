package app.model;

import app.model.users.Traveler;
import app.model.users.User;

import java.util.ArrayList;
import java.util.List;

public class SystemAdministration {
    private List<User> users;
    private static SystemAdministration instance = null;
    private SystemAdministration() {
        users = new ArrayList<>();
    }
    public static SystemAdministration initialize() {
        if (instance == null) {
            instance = new SystemAdministration();
        }

        return instance;
    }
    public void signup(String username, int birthYear,int password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username " + username + " is already being used!");
                return;
            }
        }
        this.users.add(new Traveler(username, birthYear, password));
    }

    public boolean login(String username, int password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword() == password) {
                    System.out.println("Signed in!");
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

    public void printUsers() {
        for (User user : users) {
            System.out.println("> " + user.getUsername() +
                    " is the " + user.getCurrentUserView() +
                    " born in " + user.getTravelCard().getBirthYear() + " .");
        }
    }
}
