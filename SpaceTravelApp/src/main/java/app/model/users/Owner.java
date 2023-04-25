package app.model.users;

public class Owner extends Staff {
    public Owner(String username, int birthYear, int password) {
        super(username, birthYear, password, UserType.OWNER);
    }

    @Override
    public void printMyInfo() {

    }
}
