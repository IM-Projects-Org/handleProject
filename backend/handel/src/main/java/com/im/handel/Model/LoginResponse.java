package com.im.handel.Model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@Data
public class LoginResponse {

    private String message;
    private String token;
    private String name;
    private String role;

    public LoginResponse(String message, String token, String name, String role) {
        this.message = message;
        this.token = token;
        this.name = name;
        this.role = role;
    }


}
