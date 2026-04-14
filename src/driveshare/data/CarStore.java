package driveshare.data;

import driveshare.model.Car;
import java.util.ArrayList;
import java.util.List;

// List version of a database to store all the cars

public class CarStore
{

    // Car listings
    private static List<Car> cars = new ArrayList<>();

    private static int nextId = 1;

    public static void addCar(Car car) {
        cars.add(car);
    }

    public static List<Car> getCars() {
        return cars;
    }

    public static String generateCarId() {
        return "CAR" + nextId++;
    }

    public static Car findCarById(String carId) {
        for (Car car : cars) {
            if (car.getCarId().equalsIgnoreCase(carId)) {
                return car;
            }
        }
        return null;
    }
}