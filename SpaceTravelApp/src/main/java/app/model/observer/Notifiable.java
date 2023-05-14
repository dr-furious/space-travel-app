package app.model.observer;

/**
 * Notifiable interface is implemented by all Users since they are observes that can be "notified" or rather updated
 * after the Journey changes its state
 */
public interface Notifiable {
    void update(Observable subject);
}
