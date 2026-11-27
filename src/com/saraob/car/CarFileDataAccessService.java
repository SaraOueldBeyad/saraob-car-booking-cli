package com.saraob.car;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CarFileDataAccessService implements CarDAO {

    private final String filePath;

    public CarFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Car getCarById(UUID id) {
        if (id != null) {
            List<Car> cars = getAllCars();
            for (Car car : cars) {
                if (car.getId().equals(id)) {
                    return car;
                }
            }
        }
        return null;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return Collections.emptyList();
        }
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            cars = (List<Car>) ois.readObject();
            ois.close();
            fis.close();
            return cars;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
