package com.im.handel.Service;

import com.im.handel.Entity.UserEntity;
import com.im.handel.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    private final String financialPath = "D:/handel/backend/handel/uploads/financial/";
    private final String gstCertificatePath = "D:/handel/backend/handel/uploads/gst/";

    public UserEntity registerUser(MultipartFile gst, MultipartFile financial, UserEntity user) throws IOException {
        String gstFilePath = gstCertificatePath +"_"+gst.getOriginalFilename();
        String financialFilePath = financialPath +"_"+financial.getOriginalFilename();

        // Check email already exists
        if (userRepo.existsByEmailId(user.getEmailId())) {
            throw new RuntimeException("Email already registered!");
        }
        user.setGstCertificates(gstFilePath);
        user.setFinancialCertificates(financialFilePath);
        user.setGstCertificateName(gst.getOriginalFilename());
        user.setFinancialName(financial.getOriginalFilename());

        // Encode password
        user.setRole("USER");
        user.setPassword(encoder.encode(user.getPassword()));

        if (!gst.isEmpty() && !financial.isEmpty()) {
            gst.transferTo(new File(gstFilePath));
            financial.transferTo(new File(financialFilePath));
        }

        return userRepo.save(user);
    }
}

