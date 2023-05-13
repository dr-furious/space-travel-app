package app.model.users;

public interface Payable {
    boolean pay(double amount, User to);
    boolean withdraw(double amount);
    boolean deposit(double amount);
}
