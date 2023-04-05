package app.model.users;

public class Owner extends Staff {
    public Owner(String username, int birthYear, int password) {
        super(username, birthYear, password, 9999, UserView.OWNER);
    }

    @Override
    public void printMyInfo() {

    }
}
