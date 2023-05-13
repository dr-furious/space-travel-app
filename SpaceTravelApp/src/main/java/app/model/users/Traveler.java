package app.model.users;

import app.model.journeys.ElderJourney;
import app.model.journeys.Journey;
import app.model.journeys.JourneyState;
import app.model.observer.Observable;

import java.util.ArrayList;

public class Traveler extends User {
    private ArrayList<Journey> myJourneys;
    private ArrayList<Journey> availableJourneys;
    private ArrayList<Journey> allJourneys;
    public Traveler(String username, int birthYear, int password) {
        super(username, birthYear, password, UserType.TRAVELER);
        this.allJourneys = new ArrayList<>();
        this.availableJourneys = new ArrayList<>();
        this.myJourneys = new ArrayList<>();
    }

    @Override
    public void printMyInfo() {

    }

    @Override
    public boolean pay(double amount, User to) {
        try {
            getTravelCard().decreaseBalance(amount);
        } catch (NotEnoughMoneyException exception) {
            System.out.println("Not enough money on card.");
            return false;
        }

        return to.getTravelCard().increaseBalance(amount);
    }



    @Override
    public boolean deposit(double amount) {
        return getTravelCard().increaseBalance(amount);
    }

    @Override
    public boolean withdraw(double amount) {
        try {
            getTravelCard().decreaseBalance(amount);
        } catch (NotEnoughMoneyException exception) {
            System.out.println("Not enough money on card.");
            return false;
        }

        return true;
    }

    @Override
    public void update(Observable subject) {
        if (subject.getClass() != Journey.class && subject.getClass() != ElderJourney.class ) {
            return;
        }

        Journey journey = (Journey) subject;
        JourneyState journeyState = journey.getJourneyState();

        if (journeyState == JourneyState.CANCELLED) {
            removeAvailableJourney(journey);
            removeNewJourney(journey);
            return;
        }

        if (journeyState == JourneyState.FULLY_BOOKED) {
            removeAvailableJourney(journey);
        }

        if (journeyState == JourneyState.PENDING) {
            return;
        }

        if (journeyState == JourneyState.AVAILABLE) {
            System.out.println("Traveller see ya");
            addNewJourney(journey);
            if (journey.getClass() == ElderJourney.class) {
                ElderJourney elderJourney = (ElderJourney) journey;
                if (elderJourney.getAgeThreshold() + getTravelCard().getBirthYear() > 2023) {
                    return;
                }
                addAvailableJourney(elderJourney);
                return;
            }
            addAvailableJourney(journey);
        }
    }

    private void addNewJourney(Journey journey) {
        if (allJourneys.contains(journey)) {
            return;
        }
        allJourneys.add(journey);
    }

    private void addAvailableJourney(Journey journey) {
        if (availableJourneys.contains(journey)) {
            return;
        }
        availableJourneys.add(journey);
    }

    public boolean addMyJourney(Journey journey) {
        if (pay(journey.getPrice(), journey.getAuthor())) {
            myJourneys.add(journey);
            journey.increaseLoggedUsers();
            removeNewJourney(journey);
            removeAvailableJourney(journey);
            return true;
        }

        return false;
    }

    private void removeNewJourney(Journey journey) {
        if (!allJourneys.contains(journey)) {
            return;
        }
        allJourneys.remove(journey);
    }

    private void removeAvailableJourney(Journey journey) {
        if (!availableJourneys.contains(journey)) {
            return;
        }
        availableJourneys.remove(journey);
    }

    public void removeMyJourney(Journey journey) {
        if (!myJourneys.contains(journey)) {
            return;
        }
        myJourneys.remove(journey);
        journey.decreaseLoggedUsers();
        addNewJourney(journey);
        addAvailableJourney(journey);
    }

    public ArrayList<Journey> getMyJourneys() {
        return myJourneys;
    }

    public ArrayList<Journey> getAvailableJourneys() {
        return availableJourneys;
    }

    public ArrayList<Journey> getAllJourneys() {
        return allJourneys;
    }
}
