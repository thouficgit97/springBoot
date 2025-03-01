package com.fullstack.CarRental.Controller;

import com.fullstack.CarRental.Model.Car;
import com.fullstack.CarRental.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAvailableCars() {
        List<Car> cars = carService.getAvailableCars();
        return ResponseEntity.ok(cars);
    }

    @PostMapping
    public ResponseEntity<String> addCar(@RequestBody Car car) {
        carService.addCar(car);
        return ResponseEntity.ok("Car added successfully");
    }
}