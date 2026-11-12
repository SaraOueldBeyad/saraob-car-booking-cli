package com.saraob.booking;

import java.io.*;
import java.util.Arrays;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDAO {

    private final String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveBooking(CarBooking carBooking) {
        CarBooking[] carBookings = getAllCarBookings();
        int size = carBookings.length;
        carBookings = Arrays.copyOf(carBookings, size + 1);
        carBookings[size] = carBooking;

        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(carBookings);
            os.close();
            fos.close();
            System.out.println("Car booking file has been saved successfully.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public CarBooking[] getAllCarBookings() {
        CarBooking[] carBookings = null;
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new CarBooking[0];
        }
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            carBookings = (CarBooking[]) ois.readObject();
            ois.close();
            fis.close();
            return carBookings;

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CarBooking getBookingById(UUID bookingId) {
        if (bookingId != null) {
            CarBooking[] carBookings = getAllCarBookings();
            for (CarBooking carBooking : carBookings) {
                if (carBooking.getId().equals(bookingId)) {
                    return carBooking;
                }
            }
        }
        return null;
    }

    @Override
    public CarBooking deleteBookingById(UUID bookingId) {
        if (bookingId != null) {
            CarBooking[] carBookings = getAllCarBookings();
            for (int i = 0; i < carBookings.length; i++) {
                if (carBookings[i].getId().equals(bookingId)) {
                    carBookings[i].setStatus(BookingStatus.CANCELED);
                    System.out.println("Your booking was successfully cancelled.");
                    try {
                        FileOutputStream fos = new FileOutputStream(filePath);
                        ObjectOutputStream os = new ObjectOutputStream(fos);
                        os.writeObject(carBookings);
                        os.close();
                        fos.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return carBookings[i];
                }
            }
            System.out.println("No booking with id " + bookingId + " was found.");
            return null;
        }
        return null;
    }
}
