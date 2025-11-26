package com.im.handel.Controller;

import com.im.handel.Entity.AdminEntity;
import com.im.handel.Entity.UserEntity;
import com.im.handel.Model.LoginRequest;
import com.im.handel.Model.LoginResponse;
import com.im.handel.Repository.AdminRepo;
import com.im.handel.Repository.UserRepository;
import com.im.handel.Security.JwtHelper;
import com.im.handel.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public AdminRepo adminRepo;



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserEntity user) {

        try {
            UserEntity saved = userService.registerUser(user);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String doAuthenticate(String email, String password) {

        System.out.println("Attempting authentication for email: " + email);

        // 1. Find admin from DB
        UserEntity userEntity = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Validate password (BCrypt)
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 3. Load UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // 4. Generate JWT Token
        String token = helper.generateToken(userDetails);

        System.out.println("Authentication successful");

        return token;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = this.doAuthenticate(request.getEmail(), request.getPassword());
        // UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        LoginResponse response = buildJwtResponse(request.getEmail(), token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private LoginResponse buildJwtResponse(String email,  String token) {
//        AdminEntity adminEntity = adminRepo.findByAdminEmailId(email)
//                .orElseThrow(() -> new RuntimeException("Admin not found"));

        UserEntity userEntity = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("user not found"));



        return LoginResponse.builder()
                .token(token)
                .name(userEntity.getUserName())
                .role("USER")
                .message("Login successful")
                .build();
    }



}

