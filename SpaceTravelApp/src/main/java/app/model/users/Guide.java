package app.model.users;

public class Guide extends Staff {
    private double lockedMoney;
    static {
        setAccessToken(1111);
    }

    public Guide(String username, int birthYear, int password) {
        super(username, birthYear, password, UserType.GUIDE);
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

    public void lockMoney(double amount) {
        try {
            getTravelCard().decreaseBalance(amount);
        } catch (NotEnoughMoneyException exception) {
            System.out.println("Not enough money on card.");
            return;
        }

        lockedMoney += amount;
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
