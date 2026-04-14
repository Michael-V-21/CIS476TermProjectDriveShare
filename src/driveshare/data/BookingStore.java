package driveshare.data;

import driveshare.model.Booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// List type of database for the bookings

public class BookingStore {

    // List of bookings
    private static List<Booking> bookings = new ArrayList<>();

    public static void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public static List<Booking> getBookings() {
        return bookings;
    }

    // Checks to see if car is booked and prevents users from double booking
    public static boolean isCarBooked(String carId, LocalDate newStartDate, LocalDate newEndDate) {
        for (Booking booking : bookings) {
            if (booking.getCarId().equalsIgnoreCase(carId)) {
                LocalDate existingStart = booking.getStartDate();
                LocalDate existingEnd = booking.getEndDate();

                boolean overlaps = !newEndDate.isBefore(existingStart) && !newStartDate.isAfter(existingEnd);

                if (overlaps) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Booking> getUnpaidBookingsForUser(String renterEmail) {
        List<Booking> results = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getRenterEmail().equalsIgnoreCase(renterEmail) && !booking.isPaid()) {
                results.add(booking);
            }
        }

        return results;
    }
}