package app.model.users;

import app.model.Utility;

public abstract class User implements Payable {
    private String username;
    private int password;
    private TravelCard travelCard;
    private final UserType currentUserView;
    public User(String username, int birthYear, int password, UserType currentUserView) {
        this.username = username;
        this.password = password;
        this.travelCard = new TravelCard(
                Utility.generateRandom(1_000_000_000, 10_000_000),
                birthYear,
                0
        );
        this.currentUserView = currentUserView;
    }

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

        public boolean decreaseBalance(double by) throws NotEnoughMoneyException {
            if (this.balance - by < 0 || by <= 0) {
                throw new NotEnoughMoneyException("Cannot withdraw more money than you own.");
            }
            this.balance -= by;
            return true;
        }
    }

    public String getUsername() {
        return username;
    }

    public int getPassword() {
        return password;
    }

    public TravelCard getTravelCard() {
        return travelCard;
    }

    public UserType getCurrentUserView() {
        return currentUserView;
    }

    public void setProfileName(String name) {
        this.travelCard.setName(name);
    }
    public abstract void printMyInfo(); // prints the name, birth year, function ect. to the screen
    // public abstract void loadData(); // loads the whole view for the user
}
