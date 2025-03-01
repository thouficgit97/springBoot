package com.fullstack.CarRental.Service;

import com.fullstack.CarRental.Model.Booking;
import com.fullstack.CarRental.Model.Car;
import com.fullstack.CarRental.Model.User;
import com.fullstack.CarRental.Repository.BookingRepository;
import com.fullstack.CarRental.Repository.CarRepository;
import com.fullstack.CarRental.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;


@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    public void bookCar(Booking booking) {
        User user = userRepository.findById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Car car = carRepository.findById(booking.getCar().getId())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        if (!car.isAvailable()) {
            throw new RuntimeException("Car is not available");
        }

        long days = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        if (days <= 0) {
            throw new RuntimeException("End date must be after start date");
        }

        booking.setTotalPrice(days * car.getPricePerDay());
        car.setAvailable(false);
        carRepository.save(car); // Update car availability
        bookingRepository.save(booking);
    }
}