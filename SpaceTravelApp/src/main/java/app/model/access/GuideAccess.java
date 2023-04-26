package app.model.access;

import app.model.users.Guide;
import app.model.users.Staff;
import app.model.users.Traveler;
import app.model.users.User;

import java.util.List;
import java.util.Scanner;

public class GuideAccess implements Accessible {

    @Override
    public int signup(String username, int birthYear, int password, List<User> existingUsers, int token) {
        Guide.setAccessToken(1111);
        for (User user : existingUsers) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username " + username + " is already being used!");
                return 1; // Incorrect Username Code
            }
        }
        if (token != Guide.getAccessToken()) {
            System.out.println("Incorrect access token!");
            return 3; // Incorrect Access Token Code
        }
        existingUsers.add(new Guide(username, birthYear, password));
        System.out.println("Signed in as Guide!");
        return 0; // Success Code
    }

    @Override
    public int login(String username, int password, List<User> existingUsers, int token) {
        Guide.setAccessToken(1111);
        for (User user : existingUsers) {
            if (user.getUsername().equals(username)) {
                if (user.getClass() != Guide.class) {
                    return 1;
                }
                if (user.getPassword() == password) {
                    if (token != Guide.getAccessToken()) {
                        System.out.println("Incorrect access token!");
                        return 3; // Incorrect Access Token Code
                    }
                    System.out.println("Logged in as Guide!");
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
