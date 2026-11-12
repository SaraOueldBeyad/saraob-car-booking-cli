package com.saraob.car;

public class CarService {

    CarArrayDataAccessService carArrayDataAccessService = new CarArrayDataAccessService();

    public Car[] getAllCars() {
        return carArrayDataAccessService.getAllCars();
    }
}
