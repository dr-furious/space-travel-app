package app.model.users;

import app.model.journeys.Journey;

import java.util.ArrayList;
import java.util.List;

public class Traveler extends User {
    private List<Journey> journeys;
    public Traveler(String username, int birthYear, int password) {
        super(username, birthYear, password, UserType.TRAVELER);
        this.journeys = new ArrayList<>();
    }

    private void printJourneys() {
        System.out.println("Foo\nFizz\nBuzz\nFizzBuzz");
    }

    @Override
    public void printMyInfo() {
        String name = getTravelCard().getName();
        if (name == null) {
            name = getUsername();
        }
        System.out.println("Hello, my name is " + name +
                " and I was born in " + getTravelCard().getBirthYear() +
                ". So far I've been to " + journeys.size() +
                " journeys but I can't wait to go for more! Here is the full list of my journeys: "
        );
        printJourneys();
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
}
