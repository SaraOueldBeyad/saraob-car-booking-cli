package com.saraob.booking;

import com.saraob.car.Car;
import com.saraob.car.CarDao;

import java.util.Arrays;
import java.util.UUID;

public class CarBookingDao {

    CarBooking[] carBookings = new CarBooking[20];

    public void saveBooking(CarBooking carBooking) {
        for (int i=0 ; i< carBookings.length ; i++) {
            if (carBookings[i] == null) {
                carBookings[i] = carBooking;
                return;
            }
        }
        int count = carBookings.length;
        carBookings = Arrays.copyOf(carBookings, carBookings.length * 2);
        carBookings[count] = carBooking;
    }

    public CarBooking[] getAllCarBookings() {
        CarBooking[] carBookingsTemp = new CarBooking[carBookings.length];
        int count = 0;
        for (int i=0 ; i< carBookings.length ; i++) {
            if (carBookings[i] != null) {
                carBookingsTemp[count] = carBookings[i];
                count++;
            }
        }
        carBookingsTemp = Arrays.copyOf(carBookingsTemp, count);
        return carBookingsTemp;
    }

}
