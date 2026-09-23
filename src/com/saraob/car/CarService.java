package com.saraob.car;

public class CarService {

    CarDao carDao = new CarDao();

    public Car[] getAllCars() {
        return carDao.getAllCars();
    }
}
