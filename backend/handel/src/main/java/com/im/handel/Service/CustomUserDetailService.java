package com.im.handel.Service;

import com.im.handel.Entity.AdminEntity;
import com.im.handel.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {


    @Autowired
    public AdminRepo adminRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminEntity admin = adminRepo.findByAdminEmailId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin user not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(admin.getAdminEmailId())
                .password(admin.getPassword())   // must be encoded
                .roles("ADMIN")                       // add roles from DB if needed
                .build();
    }
}

