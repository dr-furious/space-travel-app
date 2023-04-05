package app.model.users;

public class Guide extends Staff implements Payable {
    public Guide(String username, int birthYear, int password) {
        super(username, birthYear, password, 1111, UserView.GUIDE);
    }

    @Override
    public void printMyInfo() {

    }

    @Override
    public boolean pay(double amount, Staff to) {
        return false;
    }

    @Override
    public boolean deposit(double amount) {
        return false;
    }
}
