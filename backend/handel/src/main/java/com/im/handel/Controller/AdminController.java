package com.im.handel.Controller;

import com.im.handel.Entity.AdminEntity;
import com.im.handel.Model.LoginRequest;
import com.im.handel.Model.LoginResponse;
import com.im.handel.Repository.AdminRepo;
import com.im.handel.Security.JwtHelper;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    public AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtHelper helper;

    @PostMapping("/register")
    public ResponseEntity<AdminEntity> register(@Valid @RequestBody AdminEntity adminEntity) {
        adminEntity.setAdminName(adminEntity.getAdminName());
        adminEntity.setAdminEmailId(adminEntity.getAdminEmailId());
        adminEntity.setPhone(adminEntity.getPhone());
        adminEntity.setPassword(passwordEncoder.encode(adminEntity.getPassword()));
        adminRepo.save(adminEntity);

        AdminEntity saved = adminRepo.save(adminEntity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    private String doAuthenticate(String email, String password) {

        System.out.println("Attempting authentication for email: " + email);

        // 1. Find admin from DB
        AdminEntity adminEntity = adminRepo.findByAdminEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Validate password (BCrypt)
        if (!passwordEncoder.matches(password, adminEntity.getPassword())) {
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

        AdminEntity adminEntity = adminRepo.findByAdminEmailId(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        return LoginResponse.builder()
                .token(token)
                .adminName(adminEntity.getAdminName()) // better than getUsername()
                .message("Login successful")
                .build();
    }


    private boolean isAdmin(String email) {
        return "admin@handel.co.in".equals(email);
    }
}

