package com.authentication.userservice.service;

import com.authentication.userservice.Model.LoginRequest;
import com.authentication.userservice.Model.RegisterRequest;
import com.authentication.userservice.entity.User;
import com.authentication.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RedisService redisService;

    public String register(RegisterRequest request) {
        boolean exists = userRepository.existsByEmail(request.getEmail());
        if (exists) throw new RuntimeException("Email already in use");

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        return "Kayıt başarılı";
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Şifre hatalı");
        }

        String jwtToken = jwtService.generateToken(user);
        String privateKey = UUID.randomUUID().toString();
        String publicKey = UUID.randomUUID().toString();

        redisService.save(publicKey, privateKey);
        redisService.save(privateKey, jwtToken);

        return publicKey;
    }
}