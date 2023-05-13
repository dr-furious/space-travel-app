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

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

    public void logout() {
        currentUser = null;
        endSession();
        printUsers();
    }

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

    private void setObservers(Observable subject) {
        for (User user : users){
            subject.addObserver(user);
        }
    }

    private void setSubjects(Notifiable observer) {
        for (Journey journey : journeys){
            journey.addObserver(observer);
            journey.updateObservers();
        }
    }
}
