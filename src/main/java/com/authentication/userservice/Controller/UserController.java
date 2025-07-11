package com.authentication.userservice.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/me")
    public String getCurrentUser() {
        // JwtAuthenticationFilter içindeki SecurityContext’e yazdığımız email’i alıyoruz
        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return "Giriş yapan kullanıcı: " + email;
    }
}