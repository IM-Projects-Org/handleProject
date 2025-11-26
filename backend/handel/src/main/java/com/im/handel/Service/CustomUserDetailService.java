package com.im.handel.Service;

import com.im.handel.Entity.AdminEntity;
import com.im.handel.Entity.UserEntity;
import com.im.handel.Repository.AdminRepo;
import com.im.handel.Repository.UserRepository;
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

    @Autowired
    private UserRepository userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AdminEntity> adminOpt = adminRepo.findByAdminEmailId(username);
        if (adminOpt.isPresent()) {
            AdminEntity admin = adminOpt.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(admin.getAdminEmailId())
                    .password(admin.getPassword())
                    .roles("ADMIN")
                    .build();
        }

        //  Check User table
        Optional<UserEntity> userOpt = userRepo.findByEmailId(username);
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmailId())
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        }

        //  If neither found
        throw new UsernameNotFoundException("User not found");
    }
}

