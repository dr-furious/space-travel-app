package app.model.journeys;

import app.model.observer.Notifiable;
import app.model.observer.Observable;
import app.model.users.Guide;

import java.util.ArrayList;

/**
 * The top-level Journey class
 * Every Journey is being observed by users and users are updated each time it changes state
 */
public class Journey implements Observable {
    private long id;
    private String name;
    private int price;
    private int loggedUsers;
    private int capacity;
    private Guide author;
    private String date;
    private JourneyState journeyState;

    private ArrayList<Notifiable> observers;

    public Journey(long id, String name, int price, int capacity, Guide author, String date, JourneyState journeyState) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.loggedUsers = 0;
        this.capacity = capacity;
        this.author = author;
        this.date = date;
        this.journeyState = journeyState;
        this.observers = new ArrayList<>();
    }

    public Journey(long id, Guide author, String date, JourneyState journeyState) {
        this.id = id;
        this.loggedUsers = 0;
    }

    // ====== GETTERS AND SETTERS =====
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLoggedUsers() {
        return loggedUsers;
    }

    public void setLoggedUsers(int loggedUsers) {
        this.loggedUsers = loggedUsers;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Guide getAuthor() {
        return author;
    }

    public void setAuthor(Guide author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public JourneyState getJourneyState() {
        return journeyState;
    }

    public void setJourneyState(JourneyState journeyState) {
        this.journeyState = journeyState;
        updateObservers();
    }

    /**
     * @return the information about the journey
     */
    public String info() {
        return "Name: " + this.name + "\n" +
                "ID: "+ this.id + "\n" +
                "State: " + this.journeyState + "\n" +
                "Author: " + this.getAuthor().getUsername() + "\n" +
                "Price: " + this.price + "$\n" +
                "Approximate Departure Date: " + this.date + "\n" +
                "Free Seats: " + this.loggedUsers + "/" + this.capacity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Journey.class && obj.getClass() != ElderJourney.class) {
            return false;
        }

        return this.id == ((Journey) obj).getId();
    }

    @Override
    public void addObserver(Notifiable observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Notifiable observer) {
        this.observers.remove(observer);
    }

    @Override
    public void updateObservers() {
        for (Notifiable observer : observers) {
            observer.update(this);
        }
    }

    public void increaseLoggedUsers() {
        if (journeyState == JourneyState.FULLY_BOOKED) {
            return;
        }
        loggedUsers++;
        if (loggedUsers == capacity) {
            setJourneyState(JourneyState.FULLY_BOOKED);
        }
    }

    public void decreaseLoggedUsers() {
        loggedUsers--;
        if (loggedUsers < capacity) {
            setJourneyState(JourneyState.AVAILABLE);
        }
    }
}
