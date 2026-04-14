package driveshare.patterns.observer;

// Observer interface for OBSERVER PATTERN
// Classes that get updates will use this

public interface CarObserver
{
    void update(String message);
}