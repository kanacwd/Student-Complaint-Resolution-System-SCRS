package com.alaToo.scrs.controller;

import com.alaToo.scrs.dto.AuthResponse;
import com.alaToo.scrs.dto.LoginRequest;
import com.alaToo.scrs.dto.RegisterRequest;
import com.alaToo.scrs.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

}
