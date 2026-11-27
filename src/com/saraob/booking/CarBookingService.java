package com.saraob.booking;

import com.saraob.car.Car;
import com.saraob.car.CarArrayDataAccessService;
import com.saraob.car.CarDAO;
import com.saraob.user.User;
import com.saraob.user.UserArrayDataAccessService;
import com.saraob.user.UserDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CarBookingService {

    UserDAO userDAO;
    CarDAO carDAO;
    CarBookingDAO carBookingDao;

    public CarBookingService(CarBookingDAO carBookingDao, CarDAO carDAO, UserDAO userDAO) {
        this.carBookingDao = carBookingDao;
        this.carDAO = carDAO;
        this.userDAO = userDAO;
    }

    public CarBooking bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) {

        User user = userDAO.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("No user found with the provided ID!");
        }

        Car car = carDAO.getCarById(carId);
        if (car == null) {
            throw new IllegalArgumentException("No car found with the provided ID!");
        }

        if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Please enter valid dates!");
        }

        List<CarBooking> carBookings = carBookingDao.getAllCarBookings();

        for (int i = 0; i < carBookings.size(); i++) {
            if (carBookings.get(i).getCar().equals(car) &&
                    carBookings.get(i).getStatus().equals(BookingStatus.ACTIVE) &&
                    !carBookings.get(i).getStartDate().isAfter(endDate) &&
                    !carBookings.get(i).getEndDate().isBefore(startDate)) {
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

        carBookingDao.saveBooking(carBooking);

        System.out.println("You booking was successful.");

        return carBooking;
    }

    public CarBooking deleteBooking(UUID bookingId) {
        if (bookingId != null){
            CarBooking carBooking = carBookingDao.deleteBookingById(bookingId);
            return carBooking;
        }
        return null;
    }

    public List<CarBooking> getUserBookedCars(UUID userId) {
        List<CarBooking> userBooking = new ArrayList<>();
        List<CarBooking> carBookings = carBookingDao.getAllCarBookings();
        if (userId != null) {
            for (int i = 0; i < carBookings.size(); i++) {
                if (carBookings.get(i).getUser().getId().equals(userId)){
                    userBooking.add(carBookings.get(i));
                }
            }
        }
        return userBooking;
    }

    public List<CarBooking> getAllCarBookings() {
        return carBookingDao.getAllCarBookings();
    }

    public List<Car> getAvailableCars(LocalDate startDate, LocalDate endDate){
        List<Car> cars = carDAO.getAllCars();
        List<CarBooking> carBookings = carBookingDao.getAllCarBookings();
        List<Car> carsTemp = new ArrayList<>();
        boolean isBooked = false;
        if ((startDate != null) && (endDate != null) && (startDate.isBefore(endDate))){
            for (int i = 0; i < cars.size(); i++) {
                isBooked = false;
                for (int j = 0; j < carBookings.size(); j++) {
                    if (carBookings.get(j).getCar().equals(cars.get(i)) &&
                            !carBookings.get(j).getStartDate().isAfter(endDate) &&
                            !carBookings.get(j).getEndDate().isBefore(startDate) &&
                            carBookings.get(j).getStatus().equals(BookingStatus.ACTIVE)){
                        isBooked = true;
                        break;
                    }
                }
                if (!isBooked){
                   carsTemp.add(cars.get(i));
                }
            }
        }
        return carsTemp;
    }

    public List<Car> getAvailableElectricCars(LocalDate startDate, LocalDate endDate) {
        List<Car> cars = this.getAvailableCars(startDate, endDate);
        List<Car> carsTemp = new ArrayList<>();
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).isElectric()){
                carsTemp.add(cars.get(i));
            }
        }
        return carsTemp;
    }

}
