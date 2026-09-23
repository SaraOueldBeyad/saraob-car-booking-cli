package com.saraob.booking;

import com.saraob.car.Car;
import com.saraob.car.CarDao;
import com.saraob.user.User;
import com.saraob.user.UserDao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
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

        System.out.println("You booking was successful.");

        return carBooking;
    }

    public CarBooking deleteBooking(UUID bookingId) {
        CarBooking carBooking = carBookingDao.getBookingById(bookingId);
        if (carBooking != null) {
            carBooking.setStatus(BookingStatus.CANCELED);
            System.out.println("Your booking was successfully cancelled.");
            return carBooking;
        }
        return null;
    }

    public CarBooking[] getUserBookedCars(UUID userId) {
        int length = carBookingDao.getAllCarBookings().length;
        CarBooking[] userBooking = new CarBooking[length];
        CarBooking[] carBookings = carBookingDao.getAllCarBookings();
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
        return carBookingDao.getAllCarBookings();
    }

    public Car[] getAvailableCars(LocalDate startDate, LocalDate endDate){
        Car[] cars = carDao.getAllCars();
        CarBooking[] carBookings = carBookingDao.getAllCarBookings();
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
