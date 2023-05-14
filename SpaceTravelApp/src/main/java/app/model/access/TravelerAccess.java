package app.model.access;

import app.model.users.Traveler;
import app.model.users.User;
import java.util.List;

/**
 * TravelerAccess
 */
public class TravelerAccess implements Accessible {

    /**
     @param username username
      * @param birthYear birth year of the user
     * @param password user's password
     * @param existingUsers the list of existing users in the system
     * @param token token required when the Guide or Owner tries to signup
     * @return success code: 0 - correct signup, 1 - existing username, 3 - incorrect token
     *
     * Signs in a Guide
     */
    @Override
    public int signup(String username, int birthYear, int password, List<User> existingUsers, int token) {
        for (User user : existingUsers) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username " + username + " is already being used!");
                return 1; //Incorrect Username Code
            }
        }
        existingUsers.add(new Traveler(username, birthYear, password));
        System.out.println("Signed in as Traveler!");
        return 0; // Success Code
    }


    /**
     * @param username username
     * @param password user's password
     * @param existingUsers the list of existing users in the system
     * @param token token required when the Guide or Owner tries to signup
     * @return success code: 0 - correct signup, 1 - existing username, 2 - incorrect password,3 - incorrect token
     *
     * Logs in a Traveler
     */
    @Override
    public int login(String username, int password, List<User> existingUsers, int token) {
        for (User user : existingUsers) {
            if (user.getUsername().equals(username)) {
                if (user.getClass() != Traveler.class) {
                    return 1;
                }
                if (user.getPassword() == password) {
                    System.out.println("Logged in as Traveler!");
                    return 0; // Success Code
                } else {
                    System.out.println("Incorrect password. Try again!" + user.getPassword() + "|" + password);
                    return 2; // Incorrect Password Code
                }
            }
        }

        System.out.println("User with name " + username + " not found.");
        return 1; //Incorrect Username Code
    }
}
