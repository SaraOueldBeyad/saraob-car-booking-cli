package com.saraob.booking;

import java.io.*;
import java.util.*;

public class CarBookingFileDataAccessService implements CarBookingDAO {

    private final String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveBooking(CarBooking carBooking) {
        List<CarBooking> carBookings = new ArrayList<>(getAllCarBookings());
        carBookings.add(carBooking);

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
    public List<CarBooking> getAllCarBookings() {
        List<CarBooking> carBookings = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return Collections.emptyList();
        }
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            carBookings.addAll((List<CarBooking>) ois.readObject());
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
            List<CarBooking> carBookings = getAllCarBookings();
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
            List<CarBooking> carBookings = getAllCarBookings();
            for (int i = 0; i < carBookings.size(); i++) {
                if (carBookings.get(i).getId().equals(bookingId)) {
                    carBookings.get(i).setStatus(BookingStatus.CANCELED);
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
                    return carBookings.get(i);
                }
            }
            System.out.println("No booking with id " + bookingId + " was found.");
            return null;
        }
        return null;
    }
}
