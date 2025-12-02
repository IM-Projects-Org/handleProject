package com.im.handel.Model;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginResponse {

    private String message;
    private String token;
    private String name;
    private String role;




}
