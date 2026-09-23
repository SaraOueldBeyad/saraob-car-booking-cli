package com.saraob.booking;

import com.saraob.car.Car;
import com.saraob.car.CarDao;
import com.saraob.user.User;
import com.saraob.user.UserDao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {

    UserDao userDao = new UserDao();
    CarDao carDao = new CarDao();
    CarBookingDao carBookingDao = new CarBookingDao();

    public CarBooking bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) {

        User user = userDao.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("No user found with the provided ID!");
        }

        Car car = carDao.getCarById(carId);
        if (car == null) {
            throw new IllegalArgumentException("No car found with the provided ID!");
        }

        if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Please enter valid dates!");
        }

        CarBooking[] carBookings = carBookingDao.getAllCarBookings();

        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i].getCar().equals(car) && carBookings[i].getStatus().equals(BookingStatus.ACTIVE)) {
                throw new IllegalStateException("reject: the car is not available.");
            }
        }

        long days = ChronoUnit.DAYS.between(startDate, endDate);

        BigDecimal rentalPrice = car.getRentalPricePerDay().multiply(BigDecimal.valueOf(days));

        CarBooking carBooking = new CarBooking(
                UUID.randomUUID(),
                user,
                car,
                startDate,
                endDate,
                rentalPrice,
                BookingStatus.ACTIVE,
                LocalDateTime.now()
        );

        carBookingDao.saveBooking(carBooking);

        return carBooking;
    }

    public CarBooking deleteBooking(UUID bookingId) {
        CarBooking carBooking = carBookingDao.getBookingById(bookingId);
        if (carBooking != null) {
            carBooking.setStatus(BookingStatus.CANCELED);
            return carBooking;
        }
        return null;
    }

}
