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
        int size = carBookings.length + 1;
        carBookings = Arrays.copyOf(carBookings, size);
        carBookings[size - 1] = carBooking;
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(carBookings);
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
            if (fileIn.getChannel().size() == 0){
                fileIn.close();
                return new CarBooking[0];
            }
            else {
                ObjectInputStream in = new ObjectInputStream(fileIn);
                carBookings = (CarBooking[]) in.readObject();
                in.close();
            }
            fileIn.close();
        } catch (FileNotFoundException e) {
            return new CarBooking[0];
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return carBookings;
    }

    @Override
    public CarBooking getBookingById(UUID bookingId) {
        for (CarBooking carB : getAllCarBookings()){
            if (bookingId != null && carB.getId().equals(bookingId)){
                return carB;
            }
        }
        return null;
    }
}
