package app.model.access;

import app.model.users.Guide;
import app.model.users.Owner;
import app.model.users.User;

import java.util.List;
import java.util.Scanner;

public class OwnerAccess implements Accessible {

    public OwnerAccess() {
        Owner.setAccessToken(9999);
    }
    @Override
    public boolean signup(String username, int birthYear, int password, List<User> existingUsers) {
        for (User user : existingUsers) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username " + username + " is already being used!");
                return false;
            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter access token");
        int specialCode = Integer.parseInt(sc.nextLine());
        if (specialCode != Owner.getAccessToken()) {
            System.out.println("Incorrect access token!");
            return false;
        }
        existingUsers.add(new Owner(username, birthYear, password));
        System.out.println("Signed in as Owner!");
        return true;
    }

    @Override
    public boolean login(String username, int password, List<User> existingUsers) {
        for (User user : existingUsers) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword() == password) {
                    System.out.println("Enter access token");
                    Scanner sc = new Scanner(System.in);
                    int specialCode = Integer.parseInt(sc.nextLine());
                    if (specialCode != Owner.getAccessToken()) {
                        System.out.println("Incorrect access token!");
                        return false;
                    }
                    System.out.println("Logged in as Owner!");
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
