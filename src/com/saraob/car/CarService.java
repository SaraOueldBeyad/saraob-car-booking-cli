package com.saraob.car;

public class CarService {

    CarDaoArrayDataAccessService carDaoArrayDataAccessService = new CarDaoArrayDataAccessService();

    public Car[] getAllCars() {
        return carDaoArrayDataAccessService.getAllCars();
    }
}
