package app.model.observer;

public interface Observable {
    void addObserver(Notifiable observer);
    void removeObserver(Notifiable observer);
    void updateObservers();
}
