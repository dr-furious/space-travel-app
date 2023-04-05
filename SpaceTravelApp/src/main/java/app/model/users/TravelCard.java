package app.model.users;

public class TravelCard {
private String name;
private long number;
private int birthYear;
private double balance;

public TravelCard(long number, int birthYear, int balance){
        this.number = number;
        this.birthYear = birthYear;
        this.balance = balance;
        }

public String getName() {
        return name;
        }

public void setName(String name) {
        this.name = name;
        }

public long getNumber() {
        return number;
        }

public int getBirthYear() {
        return birthYear;
        }

public double getBalance() {
        return balance;
        }

public boolean increaseBalance(double by) {
        if (by <= 0) {
        return false;
        }
        this.balance += by;
        return true;
        }

public boolean decreaseBalance(double by) {
        if (this.balance - by < 0 || by <= 0) {
        return false;
        }
        this.balance -= by;
        return true;
        }
        }
