package app.model.users;

import app.model.Utility;

public abstract class User {
    private String username;
    private int password;
    private TravelCard travelCard;
    private final UserView currentUserView;
    public User(String username, int birthYear, int password, UserView currentUserView) {
        this.username = username;
        this.password = password;
        this.travelCard = new TravelCard(
                Utility.generateRandom(1_000_000_000, 10_000_000),
                birthYear,
                0
        );
        this.currentUserView = currentUserView;
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

    public UserView getCurrentUserView() {
        return currentUserView;
    }

    public abstract void printMyInfo(); // prints the name, birth year, function ect. to the screen
    public abstract void loadData(); // loads the whole view for the user
}
