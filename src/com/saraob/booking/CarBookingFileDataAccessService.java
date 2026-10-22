package com.saraob.booking;

import java.io.*;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDAO {

    private final String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveBooking(CarBooking carBooking) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath, true);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(carBooking);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public CarBooking[] getAllCarBookings() {
        CarBooking[] carBookings = null;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            carBookings = (CarBooking[]) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return carBookings;
    }

    @Override
    public CarBooking getBookingById(UUID bookingId) {
        return null;
    }
}
