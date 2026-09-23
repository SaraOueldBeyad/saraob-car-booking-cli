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

//        UserDao user = new UserDao();
//        System.out.println(user.getUserById(UUID.fromString("3f7f0c5d-83b2-4d1a-a8b2-2c2d4f9e1a11")));
//
//        CarDao car = new CarDao();
//        System.out.println(car.getCarById(UUID.fromString("e18b42d7-6c95-4a31-8f27-b903de51a684")));
//
        CarBookingDao carBookingDao = new CarBookingDao();
        CarBookingService carBookingService = new CarBookingService();

//        try {
//            CarBooking booking = new CarBooking(
//                    UUID.randomUUID(),
//                    new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "James"),
//                    new Car(UUID.fromString("e18b42d7-6c95-4a31-8f27-b903de51a684"), "TES-001", new BigDecimal("95.00"), Brand.TESLA, true),
//                    LocalDate.of(2026, 9, 25),
//                    LocalDate.of(2026, 9, 28),
//                    new BigDecimal("285.00"),
//                    BookingStatus.ACTIVE,
//                    LocalDateTime.now()
//            );
//            CarBooking booking2 = new CarBooking(
//                    UUID.randomUUID(),
//                    new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Jamila"),
//                    new Car(UUID.fromString("3c5e8a91-f240-47bd-b6e9-15a72c804df3"), "AUD-002", new BigDecimal("75.00"), Brand.AUDI, false),
//                    LocalDate.of(2026, 10, 2),
//                    LocalDate.of(2026, 10, 5),
//                    new BigDecimal("225.00"),
//                    BookingStatus.ACTIVE,
//                    LocalDateTime.now()
//            );
//
//            CarBooking booking3 = new CarBooking(
//                    UUID.randomUUID(),
//                    new User(UUID.fromString("3f7f0c5d-83b2-4d1a-a8b2-2c2d4f9e1a11"), "Sara"),
//                    new Car(UUID.fromString("5f2a9c68-d173-4eb0-a451-7d86c329f10e"), "TOY-004", new BigDecimal("55.00"), Brand.TOYOTA, false),
//                    LocalDate.of(2026, 10, 10),
//                    LocalDate.of(2026, 10, 14),
//                    new BigDecimal("220.00"),
//                    BookingStatus.ACTIVE,
//                    LocalDateTime.now()
//            );
//            carBookingDao.saveBooking(booking);
//            carBookingDao.saveBooking(booking2);
//            carBookingDao.saveBooking(booking3);
//            System.out.println(Arrays.asList(carBookingDao.getAllCarBookings()));
//        } catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//        }

        try {
//            CarBooking carBooking = carBookingService.bookCar(
//                   UUID.fromString("3f7f0c5d-83b2-4d1a-a8b2-2c2d4f9e1a11"),
//                   UUID.fromString("e18b42d7-6c95-4a31-8f27-b903de51a684"),
//                   LocalDate.now(),
//                   LocalDate.of(2026,9,25)
//            );
//            System.out.println(carBooking.toString());
//
//            System.out.println(carBookingService.deleteBooking(carBooking.getId()).toString());

            CarBooking booking1 = carBookingService.bookCar(
                    UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"),
                    UUID.fromString("a7d91f3c-2b84-4e65-9a12-cf8301d6b742"),
                    LocalDate.of(2026, 9, 23),
                    LocalDate.of(2026, 9, 25)
            );

            CarBooking booking2 = carBookingService.bookCar(
                    UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"),
                    UUID.fromString("3c5e8a91-f240-47bd-b6e9-15a72c804df3"),
                    LocalDate.of(2026, 9, 27),
                    LocalDate.of(2026, 9, 28)
            );

            CarBooking booking3 = carBookingService.bookCar(
                    UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"),
                    UUID.fromString("5f2a9c68-d173-4eb0-a451-7d86c329f10e"),
                    LocalDate.of(2026, 10, 3),
                    LocalDate.of(2026, 10, 5)
            );

//            carBookingService.getAllCarBookings();
            for (Car car : carBookingService.getAvailableCars(LocalDate.of(2026,9,23), LocalDate.of(2026,9,28))){
                System.out.println(car.toString());
            }

        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Please provide a valid booking ID.");
        }
    }
}
