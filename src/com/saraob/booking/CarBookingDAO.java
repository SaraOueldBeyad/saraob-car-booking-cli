package com.saraob.booking;

import java.util.UUID;

public interface CarBookingDAO {
    void saveBooking(CarBooking carBooking);
    CarBooking[] getAllCarBookings();
    CarBooking getBookingById(UUID bookingId);
}
