package com.saraob.booking;

import java.util.UUID;

public interface CarBookingDAO {
    public void saveBooking(CarBooking carBooking);
    public CarBooking[] getAllCarBookings();
    public CarBooking getBookingById(UUID bookingId);
}
