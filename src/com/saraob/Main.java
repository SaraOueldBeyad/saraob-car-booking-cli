package com.saraob;

import com.saraob.booking.BookingStatus;
import com.saraob.booking.CarBooking;
import com.saraob.booking.CarBookingDao;
import com.saraob.booking.CarBookingService;
import com.saraob.car.Brand;
import com.saraob.car.Car;
import com.saraob.car.CarDao;
import com.saraob.user.User;
import com.saraob.user.UserDao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        UserDao user = new UserDao();
        System.out.println(user.getUserById(UUID.fromString("3f7f0c5d-83b2-4d1a-a8b2-2c2d4f9e1a11")));

        CarDao car = new CarDao();
        System.out.println(car.getCarById(UUID.fromString("e18b42d7-6c95-4a31-8f27-b903de51a684")));

        CarBookingDao carBookingDao = new CarBookingDao();
        CarBookingService carBookingService = new CarBookingService();

        try {
            CarBooking booking = new CarBooking(
                    UUID.randomUUID(),
                    new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "James"),
                    new Car(UUID.fromString("a7d91f3c-2b84-4e65-9a12-cf8301d6b742"), "TES-001", new BigDecimal("95.00"), Brand.TESLA, true),
                    LocalDate.of(2026, 9, 25),
                    LocalDate.of(2026, 9, 28),
                    new BigDecimal("285.00"),
                    BookingStatus.ACTIVE,
                    LocalDateTime.now()
            );
            carBookingDao.saveBooking(booking);
            System.out.println(Arrays.asList(carBookingDao.getAllCarBookings()));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
