package com.fullstack.CarRental.Repository;

import com.fullstack.CarRental.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);

    boolean existsByName(String name);
}