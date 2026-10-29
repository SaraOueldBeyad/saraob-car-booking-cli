package com.saraob.car;

import java.util.UUID;

public interface CarDAO {
    Car getCarById(UUID id);
    Car[] getAllCars();
}
