package com.saraob.booking;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CarBookingDAO {

    public void saveBooking(CarBooking carBooking);
    public List<CarBooking> getAllCarBookings();
    public CarBooking getBookingById(UUID bookingId);
    public CarBooking deleteBookingById(UUID bookingId);

}
