package com.saraob.booking;

import com.saraob.car.Car;
import com.saraob.car.CarArrayDataAccessService;
import com.saraob.user.User;
import com.saraob.user.UserArrayDataAccessService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;

public class CarBookingService {

    UserArrayDataAccessService userArrayDataAccessService = new UserArrayDataAccessService();
    CarArrayDataAccessService carArrayDataAccessService = new CarArrayDataAccessService();
    CarBookingArrayDataAccessService carBookingArrayDataAccessService = new CarBookingArrayDataAccessService();

    public CarBooking bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) {

        User user = userArrayDataAccessService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("No user found with the provided ID!");
        }

        Car car = carArrayDataAccessService.getCarById(carId);
        if (car == null) {
            throw new IllegalArgumentException("No car found with the provided ID!");
        }

        if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Please enter valid dates!");
        }

        CarBooking[] carBookings = carBookingArrayDataAccessService.getAllCarBookings();

        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i].getCar().equals(car) &&
                    carBookings[i].getStatus().equals(BookingStatus.ACTIVE) &&
                    !carBookings[i].getStartDate().isAfter(endDate) &&
                    !carBookings[i].getEndDate().isBefore(startDate)) {
                throw new IllegalStateException("reject: the car is not available.");
            }
        }

        long days = ChronoUnit.DAYS.between(startDate, endDate);

        if (days == 0) {
            throw new IllegalArgumentException("Start date and end date cannot be the same day. \n" +
                    "A booking must be at least 24 hours.");
        }

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

        carBookingArrayDataAccessService.saveBooking(carBooking);

        System.out.println("You booking was successful.");

        return carBooking;
    }

    public CarBooking deleteBooking(UUID bookingId) {
        CarBooking carBooking = carBookingArrayDataAccessService.getBookingById(bookingId);
        if (carBooking != null) {
            carBooking.setStatus(BookingStatus.CANCELED);
            System.out.println("Your booking was successfully cancelled.");
            return carBooking;
        }
        return null;
    }

    public CarBooking[] getUserBookedCars(UUID userId) {
        int length = carBookingArrayDataAccessService.getAllCarBookings().length;
        CarBooking[] userBooking = new CarBooking[length];
        CarBooking[] carBookings = carBookingArrayDataAccessService.getAllCarBookings();
        int count = 0;
        if (userId != null) {
            for (int i = 0; i < length; i++) {
                if (carBookings[i].getUser().getId().equals(userId)){
                    userBooking[count] = carBookings[i];
                    count++;
                }
            }
        }
        userBooking = Arrays.copyOf(userBooking, count);
        return userBooking;
    }

    public CarBooking[] getAllCarBookings() {
        return carBookingArrayDataAccessService.getAllCarBookings();
    }

    public Car[] getAvailableCars(LocalDate startDate, LocalDate endDate){
        Car[] cars = carArrayDataAccessService.getAllCars();
        CarBooking[] carBookings = carBookingArrayDataAccessService.getAllCarBookings();
        Car[] carsTemp = new Car[cars.length];
        boolean isBooked = false;
        int count = 0;
        if ((startDate != null) && (endDate != null) && (startDate.isBefore(endDate))){
            for (int i = 0; i < cars.length; i++) {
                isBooked = false;
                for (int j = 0; j < carBookings.length; j++) {
                    if (carBookings[j].getCar().equals(cars[i]) &&
                            !carBookings[j].getStartDate().isAfter(endDate) &&
                            !carBookings[j].getEndDate().isBefore(startDate) &&
                            carBookings[j].getStatus().equals(BookingStatus.ACTIVE)){
                        isBooked = true;
                        break;
                    }
                }
                if (!isBooked){
                   carsTemp[count] = cars[i];
                   count++;
                }
            }
        }
        carsTemp = Arrays.copyOf(carsTemp, count);
        return carsTemp;
    }

    public Car[] getAvailableElectricCars(LocalDate startDate, LocalDate endDate) {
        Car[] cars = this.getAvailableCars(startDate, endDate);
        Car[] carsTemp = new Car[cars.length];
        int count = 0;
        for (int i = 0; i < cars.length; i++) {
            if (cars[i].isElectric()){
                carsTemp[count] = cars[i];
                count++;
            }
        }
        carsTemp = Arrays.copyOf(carsTemp, count);
        return carsTemp;
    }

}
