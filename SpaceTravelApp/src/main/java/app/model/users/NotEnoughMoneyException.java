package app.model.users;

/**
 * Exception that is thrown after a user tries to pay, but he has not enough money on its TravelCard
 */
public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
