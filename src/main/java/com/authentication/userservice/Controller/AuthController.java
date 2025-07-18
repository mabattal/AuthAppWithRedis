package com.authentication.userservice.Controller;

import com.authentication.userservice.Model.LoginRequest;
import com.authentication.userservice.Model.RegisterRequest;
import com.authentication.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("X-PUBLIC-KEY") String publicKey) {
        authService.logout(publicKey);
        return ResponseEntity.ok("Çıkış yapıldı");
    }
}