package com.fullstack.CarRental.Controller;

import com.fullstack.CarRental.Model.Booking;
import com.fullstack.CarRental.Model.User;
import com.fullstack.CarRental.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping
    public ResponseEntity<String> bookCar(@RequestBody Booking booking, @RequestParam Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().body("You must be logged in to book a car");
        }
        User user = new User();
        user.setId(userId);
        booking.setUser(user);
        bookingRepository.save(booking);
        return ResponseEntity.ok("Booking created successfully");
    }

    @GetMapping("/user")
    public ResponseEntity<List<Booking>> getUserBookings(@RequestParam Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return ResponseEntity.ok(bookings);
    }
}