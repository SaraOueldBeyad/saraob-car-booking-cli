package com.saraob.car;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;

public class CarFileDataAccessService implements CarDAO {

    private final String filePath;

    public CarFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Car getCarById(UUID id) {
        if (id != null) {
            Car[] cars = getAllCars();
            for (Car car : cars) {
                if (car.getId().equals(id)) {
                    return car;
                }
            }
        }
        return null;
    }

    @Override
    public Car[] getAllCars() {
        Car[] cars = null;
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new Car[0];
        }
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            cars = (Car[]) ois.readObject();
            ois.close();
            fis.close();
            return cars;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
