package com.saraob;

import com.saraob.booking.BookingStatus;
import com.saraob.booking.CarBooking;
import com.saraob.booking.CarBookingFileDataAccessService;
import com.saraob.car.Brand;
import com.saraob.car.Car;
import com.saraob.user.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class MainTest {
    static void main(String[] args) {

        CarBookingFileDataAccessService carBookingFileDataAccessService = new CarBookingFileDataAccessService("carbooking.ser");
        CarBooking samiraBooking = new CarBooking(
                UUID.randomUUID(),
                new User(
                        UUID.fromString("6ba7b810-9dad-41d1-80b4-00c04fd430c8"),
                        "Samira"
                ),
                new Car(
                        UUID.fromString("9b2e4f61-7c3a-48d5-912f-65a831c720de"),
                        "T-001",
                        BigDecimal.valueOf(80),
                        Brand.TESLA,
                        true
                ),
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 4),
                BigDecimal.valueOf(240),
                BookingStatus.ACTIVE,
                LocalDateTime.now()
        );
        CarBooking jamesBooking = new CarBooking(
                UUID.randomUUID(),
                new User(
                        UUID.randomUUID(),
                        "James"
                ),
                new Car(
                        UUID.randomUUID(),
                        "B-002",
                        BigDecimal.valueOf(65),
                        Brand.MERCEDES,
                        false
                ),
                LocalDate.of(2026, 11, 10),
                LocalDate.of(2026, 11, 15),
                BigDecimal.valueOf(325),
                BookingStatus.ACTIVE,
                LocalDateTime.now()
        );

        carBookingFileDataAccessService.saveBooking(samiraBooking);
        carBookingFileDataAccessService.saveBooking(jamesBooking);
//        try (
//                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("carbooking.ser"))) {
//                out.writeObject(carBookings);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        for (CarBooking carBooking : carBookingFileDataAccessService.getAllCarBookings()){
            System.out.println(carBooking);
        }

    }
}
