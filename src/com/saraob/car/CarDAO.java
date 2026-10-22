package com.saraob.car;

import java.util.UUID;

public interface CarDAO {
    public Car getCarById(UUID id);
    public Car[] getAllCars();
}
