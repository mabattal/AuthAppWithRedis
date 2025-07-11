package com.authentication.userservice.Model;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
}