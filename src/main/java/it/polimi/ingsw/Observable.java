package it.polimi.ingsw;
import java.util.ArrayList;
import java.util.List;


public class Observable {
    private final List<Observer> observers= new ArrayList<>();

    /**
     * Adds an observer to the observer list
     * @param observer the observer to add
     */
    public void addObserver(Observer observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }
    /**
     * Removes an observer from the observers list
     * @param observer the observer to be removed
     */
    public void removeObserver(Observer observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

}
