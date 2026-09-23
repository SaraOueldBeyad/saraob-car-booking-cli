package com.saraob;

import com.saraob.booking.CarBooking;
import com.saraob.booking.CarBookingService;
import com.saraob.car.Car;
import com.saraob.user.User;
import com.saraob.user.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);
        CarBookingService carBookingService = new CarBookingService();
        int choice = 0;

        do {
            System.out.println("""
                    1 - Book Car
                    2 - Delete Booking
                    3 - View All User Booked Cars
                    4 - View All Bookings
                    5 - View Available Cars
                    6 - View Available Electric Cars
                    7 - View All Users
                    8 - Exit
                    """);
            try {
                System.out.print("Please select an option from the menu: ");
                choice = scnr.nextInt();
                scnr.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number from the menu: ");
                scnr.nextLine();
            }

            switch (choice) {
                case 1 -> {
                    try {
                        System.out.print("Please enter user ID: ");
                        UUID userId = UUID.fromString(scnr.nextLine());

                        System.out.print("Please enter car ID: ");
                        UUID carId = UUID.fromString(scnr.nextLine());

                        System.out.print("Please enter a valid start date (YYYY-MM-DD): ");
                        LocalDate startDate = LocalDate.parse(scnr.nextLine());

                        System.out.print("Please enter a valid end date (YYYY-MM-DD): ");
                        LocalDate endDate = LocalDate.parse(scnr.nextLine());

                        System.out.println(carBookingService.bookCar(userId, carId, startDate, endDate));

                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    } catch (DateTimeParseException e) {
                        System.out.println("The provided date is invalid.");
                    }
                }
                case 2 -> {
                    try {
                        System.out.print("Please enter your booking ID: ");
                        UUID bookingId = UUID.fromString(scnr.nextLine());
                        System.out.println(carBookingService.deleteBooking(bookingId));
                    } catch (IllegalArgumentException e) {
                        System.out.println("The provided booking ID is invalid.");
                    }
                }
                case 3 -> {
                    try {
                        System.out.print("Please enter user ID: ");
                        UUID userId = UUID.fromString(scnr.nextLine());

                        CarBooking[] userBookings = carBookingService.getUserBookedCars(userId);

                        if (userBookings.length == 0) {
                            System.out.println("User has no bookings.");
                        } else {
                            for (CarBooking carBooking : userBookings) {
                                System.out.println(carBooking);
                            }
                        }

                    } catch (IllegalArgumentException e) {
                        System.out.println("The provided user ID is invalid.");
                    }
                }
                case 4 -> {
                    CarBooking[] carBookings = carBookingService.getAllCarBookings();
                    if (carBookings.length == 0){
                        System.out.println("There is no bookings.");
                    } else {
                        for (CarBooking carBooking : carBookings){
                            System.out.println(carBooking);
                        }
                    }
                }
                case 5 -> {
                    try {
                        System.out.print("Please enter a valid start date (YYYY-MM-DD): ");
                        LocalDate startDate = LocalDate.parse(scnr.nextLine());

                        System.out.print("Please enter a valid end date (YYYY-MM-DD): ");
                        LocalDate endDate = LocalDate.parse(scnr.nextLine());

                        Car[] cars = carBookingService.getAvailableCars(startDate, endDate);

                        if (cars.length == 0){
                            System.out.println("There is no available cars for the provided dates.");
                        } else {
                            for (Car car : cars){
                                System.out.println(car);
                            }
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("The provided date is invalid.");
                    }
                }
                case 6 -> {
                    try {
                        System.out.print("Please enter a valid start date (YYYY-MM-DD): ");
                        LocalDate startDate = LocalDate.parse(scnr.nextLine());

                        System.out.print("Please enter a valid end date (YYYY-MM-DD): ");
                        LocalDate endDate = LocalDate.parse(scnr.nextLine());

                        Car[] electricCars = carBookingService.getAvailableElectricCars(startDate, endDate);

                        if (electricCars.length == 0){
                            System.out.println("There is no available electric cars for the provided dates.");
                        } else {
                            for (Car car : electricCars){
                                System.out.println(car);
                            }
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("The provided date is invalid.");
                    }
                }
                case 7 -> {
                    UserService userService = new UserService();
                    for (User user : userService.getAllUsers()){
                        System.out.println(user);
                    }
                }
                case 8 -> System.out.println("Exit.");
                default -> System.out.println("Please provide a valid number from the menu.");
            }
            System.out.println();
        } while (choice != 8);

    }
}
