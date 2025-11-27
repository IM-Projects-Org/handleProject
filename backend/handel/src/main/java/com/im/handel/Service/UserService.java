package com.im.handel.Service;

import com.im.handel.Entity.UserEntity;
import com.im.handel.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    public UserEntity registerUser(UserEntity user) {

        // Check email already exists
        if (userRepo.existsByEmailId(user.getEmailId())) {
            throw new RuntimeException("Email already registered!");
        }

        // Encode password
        user.setRole("USER");
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepo.save(user);
    }
}

