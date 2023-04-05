package app.model.users;

public interface Payable {
    boolean pay(double amount, Staff to);
    boolean deposit(double amount);
}
