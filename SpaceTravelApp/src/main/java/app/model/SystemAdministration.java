package app.model;

import app.model.access.AccessContext;
import app.model.access.GuideAccess;
import app.model.access.OwnerAccess;
import app.model.access.TravelerAccess;
import app.model.users.Traveler;
import app.model.users.User;
import app.model.users.UserType;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.List;

public class SystemAdministration {

    private AccessContext accessContext;
    private List<User> users;
    private static SystemAdministration instance = null;
    private SystemAdministration() {
        users = new ArrayList<>();
        accessContext = new AccessContext();
    }
    public static SystemAdministration initialize() {
        if (instance == null) {
            instance = new SystemAdministration();
        }
        return instance;
    }

    public void setAccessContext(UserType userType) {
        switch (userType) {
            case TRAVELER -> {
                accessContext.setAccessStrategy(new TravelerAccess());
            }
            case GUIDE -> {
                accessContext.setAccessStrategy(new GuideAccess());
            }
            case OWNER -> {
                accessContext.setAccessStrategy(new OwnerAccess());
            }
            default -> {}
        }
    }

    public boolean signup(String username, int password, int birthYear) {
        return accessContext.signup(username, password, birthYear, users);
    }

    public boolean login(String username, int password) {
        return accessContext.login(username, password, users);
    }

    public void printUsers() {
        for (User user : users) {
            System.out.println("> " + user.getUsername() +
                    " is the " + user.getCurrentUserView() +
                    " born in " + user.getTravelCard().getBirthYear() + " .");
        }
    }
}
