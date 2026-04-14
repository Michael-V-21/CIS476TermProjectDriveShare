package driveshare.patterns.observer;

import java.util.ArrayList;
import java.util.List;

// Subject in OBSERVER PATTERN

public class CarSubject
{
    // List of observers watching cars
    private List<CarObserver> observers = new ArrayList<>();

    public void addObserver(CarObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CarObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (CarObserver observer : observers) {
            observer.update(message);
        }
    }

    public List<CarObserver> getObservers() {
        return observers;
    }
}