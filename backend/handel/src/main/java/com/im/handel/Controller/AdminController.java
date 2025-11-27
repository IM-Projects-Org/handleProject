package com.im.handel.Controller;

import com.im.handel.Entity.AdminEntity;
import com.im.handel.Model.LoginRequest;
import com.im.handel.Model.LoginResponse;
import com.im.handel.Repository.AdminRepo;
import com.im.handel.Security.JwtHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtHelper helper;

    // --------------------------------------
    // REGISTER ADMIN
    // --------------------------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AdminEntity adminEntity) {

        // NULL CHECK (prevents crash)
        if (adminEntity.getPassword() == null || adminEntity.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("Password cannot be empty");
        }

        if (adminEntity.getAdminEmailId() == null || adminEntity.getAdminEmailId().isBlank()) {
            return ResponseEntity.badRequest().body("Email cannot be empty");
        }

        if (adminRepo.findByAdminEmailId(adminEntity.getAdminEmailId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already registered");
        }

        // Encode password
        adminEntity.setPassword(passwordEncoder.encode(adminEntity.getPassword()));

        // Default role if not provided
        if (adminEntity.getRole() == null || adminEntity.getRole().isBlank()) {
            adminEntity.setRole("ADMIN");
        }

        AdminEntity saved = adminRepo.save(adminEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // --------------------------------------
    // AUTHENTICATION
    // --------------------------------------
    private String doAuthenticate(String email, String rawPassword) {

        AdminEntity adminEntity = adminRepo.findByAdminEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, adminEntity.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return helper.generateToken(userDetails);
    }

    // --------------------------------------
    // LOGIN
    // --------------------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            String token = this.doAuthenticate(request.getEmail(), request.getPassword());

            LoginResponse response = buildJwtResponse(request.getEmail(), token);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponse.builder()
                            .status(false)
                            .message(ex.getMessage())
                            .build());
        }
    }

    // --------------------------------------
    // BUILD LOGIN RESPONSE
    // --------------------------------------
    private LoginResponse buildJwtResponse(String email, String token) {

        AdminEntity admin = adminRepo.findByAdminEmailId(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        return LoginResponse.builder()
                .status(true)
                .token(token)
                .name(admin.getAdminName())
                .role(admin.getRole())
                .message("Login successful")
                .build();
    }
}
