package app.model;

import app.model.access.AccessContext;
import app.model.access.GuideAccess;
import app.model.access.OwnerAccess;
import app.model.access.TravelerAccess;
import app.model.journeys.Journey;
import app.model.journeys.JourneyState;
import app.model.observer.Notifiable;
import app.model.observer.Observable;
import app.model.users.*;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The SystemAdministration utilizes a Singleton design pattern - there always exists only one instance of this class
 * that is then shared across all controllers. It keeps the journeys created and users signed in
 */
public class SystemAdministration {

    private AccessContext accessContext;
    private ArrayList<User> users;
    private ArrayList<Journey> journeys;
    private User currentUser;
    private Timer timer;
    private long sessionDuration;
    private static SystemAdministration instance = null;
    private SystemAdministration() {
        users = new ArrayList<>();
        journeys = new ArrayList<>();
        accessContext = new AccessContext();
        sessionDuration = 0;
        timer = new Timer();
    }
    public static SystemAdministration initialize() {
        if (instance == null) {
            instance = new SystemAdministration();
        }
        return instance;
    }

    /**
     * @param userType type of User
     * Based on the User type, the according access strategy (for signup and login) is set
     */
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

    public AccessContext getAccessContext() {
        return this.accessContext;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public long getSessionDuration() {
        return sessionDuration;
    }

    public ArrayList<Journey> getJourneys() {
        return journeys;
    }

    public int signup(String username, int password, int birthYear, int token) {
        int responseCode = accessContext.signup(username, password, birthYear, users, token);
        if (responseCode == 0) {
            setCurrentUser(username);
            setSubjects(currentUser);
            startSession();
        }

        return responseCode;
    }

    public int login(String username, int password, int token) {
        int responseCode = accessContext.login(username, password, users, token);
        if (responseCode == 0) {
            setCurrentUser(username);
            startSession();
        }

        return responseCode;
    }

    /**
     * Signs out currently logged User, ends his session and prints all users to the console
     */
    public void logout() {
        currentUser = null;
        endSession();
        printUsers();
    }

    /**
     * @param username username of a User that is currently logged in
     *
     * Sets the currently logged user
     */
    private void setCurrentUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                currentUser = user;
            }
        }
    }

    public void printUsers() {
        for (User user : users) {
            System.out.println("> " + user.getUsername() +
                    " is the " + user.getCurrentUserView() +
                    " born in " + user.getTravelCard().getBirthYear() + " .");
        }
    }

    private void startSession() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sessionDuration++;
            }
        }, 0, 1000);
    }

    private void endSession() {
        timer.cancel();
        sessionDuration = 0;
        timer = new Timer();
    }

    public void addJourney(Journey journey) {
        journeys.add(journey);
        setObservers(journey);
        journey.setJourneyState(JourneyState.PENDING);
    }


    /**
     * @param subject journey that was recently added to the system
     *                After the new journey is added to the system, it updates all user about its state
     */
    private void setObservers(Observable subject) {
        for (User user : users){
            subject.addObserver(user);
        }
    }

    /**
     * @param observer user that was recently added to the system
     *                After the new user is added to the system, it is updated by all journeys about their state
     */
    private void setSubjects(Notifiable observer) {
        for (Journey journey : journeys){
            journey.addObserver(observer);
            journey.updateObservers();
        }
    }
}
