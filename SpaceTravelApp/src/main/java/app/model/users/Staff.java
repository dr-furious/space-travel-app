package app.model.users;

public abstract class Staff extends User {
    private int key;
    public Staff(String username, int birthYear, int password, int key, UserView userView) {
        super(username, birthYear, password, userView);
        this.key = key;
    }
}
