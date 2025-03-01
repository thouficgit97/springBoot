package com.fullstack.CarRental.Service;

import com.fullstack.CarRental.Model.User;
import com.fullstack.CarRental.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String register(User user) {
        if (userRepository.existsByName(user.getName())) {
            return "Username is already taken";
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Email is already taken";
        }
        if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            return "Invalid email format";
        }
        userRepository.save(user);
        return "User registered successfully";
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user.getId().toString(); // Return userId as a string
        }
        return "Invalid email or password";
    }
}