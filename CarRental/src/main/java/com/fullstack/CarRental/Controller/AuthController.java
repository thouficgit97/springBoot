package com.fullstack.CarRental.Controller;

import com.fullstack.CarRental.Model.User;
import com.fullstack.CarRental.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String result = userService.register(user);
        if (result.equals("User registered successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String result = userService.login(user.getEmail(), user.getPassword());
        if (result.startsWith("Invalid")) {
            return ResponseEntity.badRequest().body(result);
        } else {
            return ResponseEntity.ok(result); // Returns userId as a string
        }
    }
}