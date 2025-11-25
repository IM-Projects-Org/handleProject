package com.im.handel.Model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class LoginResponse {

    private String message;
    private String token;
    private String adminName;

    public LoginResponse(String message, String token, String adminName) {
        this.message = message;
        this.token = token;
        this.adminName = adminName;
    }


}
