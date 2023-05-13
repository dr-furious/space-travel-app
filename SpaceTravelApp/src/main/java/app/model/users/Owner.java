package app.model.users;

public class Owner extends Staff {
    public Owner(String username, int birthYear, int password) {
        super(username, birthYear, password, UserType.OWNER);
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
}
