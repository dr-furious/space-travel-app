package app.model.users;


/**
 * Payable interface is implemented by all Users
 */
public interface Payable {
    boolean pay(double amount, User to);
    boolean withdraw(double amount);
    boolean deposit(double amount);
}
