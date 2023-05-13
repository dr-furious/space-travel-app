package app.model.users;

import app.model.journeys.ElderJourney;
import app.model.journeys.Journey;
import app.model.journeys.JourneyState;
import app.model.observer.Observable;

import java.util.ArrayList;

public class Owner extends Staff {
    private ArrayList<Journey> newJourneys;
    private ArrayList<Journey> allJourneys;
    public Owner(String username, int birthYear, int password) {
        super(username, birthYear, password, UserType.OWNER);
        newJourneys = new ArrayList<>();
        allJourneys = new ArrayList<>();
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

    public ArrayList<Journey> getNewJourneys() {
        return newJourneys;
    }

    public ArrayList<Journey> getAllJourneys() {
        return allJourneys;
    }

    @Override
    public void update(Observable subject) {
        if (subject.getClass() != Journey.class && subject.getClass() != ElderJourney.class ) {
            return;
        }

        Journey journey = (Journey) subject;
        JourneyState journeyState = journey.getJourneyState();

        if (journeyState == JourneyState.PENDING) {
            System.out.println("Owner seeeee ya");
            newJourneys.add(journey);
            return;
        }

        if (journeyState == JourneyState.CANCELLED || journeyState == JourneyState.AVAILABLE) {
            allJourneys.add(journey);
            newJourneys.remove(journey);
        }
    }

    public void approveJourney(Journey journey) {
        journey.setJourneyState(JourneyState.AVAILABLE);
    }

    public void declineJourney(Journey journey) {
        journey.setJourneyState(JourneyState.CANCELLED);
    }
}
