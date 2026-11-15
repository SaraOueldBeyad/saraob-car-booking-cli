package com.saraob.car;

public class CarService {

    CarDAO carDAO;

    public CarService(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public Car[] getAllCars() {
        return carDAO.getAllCars();
    }
}
