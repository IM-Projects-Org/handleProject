package com.im.handel.Model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoginResponse {

    private boolean status;   // ✔ correct field name + correct type
    private String message;
    private String token;
    private String name;
    private String role;
}
