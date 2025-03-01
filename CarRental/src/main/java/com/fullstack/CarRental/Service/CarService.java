package com.fullstack.CarRental.Service;

import com.fullstack.CarRental.Model.Car;
import com.fullstack.CarRental.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAvailableCars() {
        return carRepository.findAll().stream()
                .filter(Car::isAvailable)
                .collect(Collectors.toList());
    }

    public void addCar(Car car) {
        carRepository.save(car);
    }
}