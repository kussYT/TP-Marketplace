package com.insa.marketplace.controller;

import com.insa.marketplace.dto.LoginRequest;
import com.insa.marketplace.dto.UserDto;
import com.insa.marketplace.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            UserDto user = authService.login(request);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            // mauvais mot de passe
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
