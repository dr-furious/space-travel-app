package app.model.observer;

/**
 * Observable interface is implemented by all Journeys since they are subjects that can be "observed"
 */
public interface Observable {
    void addObserver(Notifiable observer);
    void removeObserver(Notifiable observer);
    void updateObservers();
}
