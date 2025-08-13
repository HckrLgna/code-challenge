package com.lgnasolutions.backend_challenge.domain.ports;

import com.lgnasolutions.backend_challenge.domain.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository {
    User registerUser (User user);
    Optional<User> findUserByEmail(String email);
}
