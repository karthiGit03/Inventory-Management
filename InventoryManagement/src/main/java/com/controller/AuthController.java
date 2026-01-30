package com.controller;

import org.springframework.web.bind.annotation.*;

import com.dto.*;
import com.entity.User;
import com.exception.InvalidCredentialsException;
import com.repository.UserRepository;
import com.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        User user = userRepository.findByUsername(request.username)
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.getPassword().equals(request.password)) {
            throw new InvalidCredentialsException();
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        LoginResponse response = new LoginResponse();
        response.token = token;
        return response;
    }
}
