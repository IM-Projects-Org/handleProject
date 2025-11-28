package com.im.handel.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Data
@Table(name = "corporate_registration")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="corporate_id")
    private Long corporateId;

    @Column(name="domestic_Material_Id")
    private Long domesticMaterialId;

    @Column(name="International_Material_Id")
    private Long internationalMaterialId;

    @Column(name="entityName")
    private String entityName;
    @Column(name="iecCode")
    private String iecCode;
    @Column(name="gstCertificates")
    private String gstCertificates;
    @Column(name="financialCertificates")
    private String financialCertificates;
    @Column(name="userName")
    private String userName;
    @Column(name="mobileNumber")
    private Long mobileNumber;
    @Column(name="emailId")
    @Email(message = "Enter a valid email")
    private String emailId;
    @Column(name="beneficiary")
    private String beneficiary;
    @Column(name="accountNumber")
    private String accountNumber;
    @Column(name="swiftCode")
    private String swiftCode;

    @Column(name="Password")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Column(name="gstCertificateName")
    private String gstCertificateName;

    @Column(name="financialName")
    private String financialName;

    @Column(name="Admin_Id")
    private Long adminId;

    @Column(name="status")
    private String status;

    @Column(name="loginCount")
    private String loginCount;

    @Column(name="role")
    private String role;

    public UserEntity() {
    }
}
