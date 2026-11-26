package com.saraob.booking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CarBookingDaoArrayDataAccessService implements CarBookingDAO {

    List<CarBooking> carBookings = new ArrayList<>();

    public void saveBooking(CarBooking carBooking) {
        carBookings.add(carBooking);
    }

    public List<CarBooking> getAllCarBookings() {
        return carBookings;
    }

    public CarBooking getBookingById(UUID bookingId) {
        if (bookingId != null) {
            List<CarBooking> carBookingTab = this.getAllCarBookings();
            for (int i = 0; i < carBookingTab.size(); i++) {
                if (carBookingTab.get(i).getId().equals(bookingId)) {
                    return carBookingTab.get(i);
                }
            }
        }
        return null;
    }

    public CarBooking deleteBookingById(UUID bookingId) {
        CarBooking carBooking = this.getBookingById(bookingId);
        if (carBooking != null) {
            carBooking.setStatus(BookingStatus.CANCELED);
            System.out.println("Your booking was successfully cancelled.");
            return carBooking;
        }
        System.out.println("Your booking was not found.");
        return null;
    }
}
