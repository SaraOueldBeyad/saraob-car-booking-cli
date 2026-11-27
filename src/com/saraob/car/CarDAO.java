package com.saraob.car;

import java.util.List;
import java.util.UUID;

public interface CarDAO {

    public Car getCarById(UUID id);
    public List<Car> getAllCars();

}
