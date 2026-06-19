package com.bridgelabz.fundoo_notes.controller;


import com.bridgelabz.fundoo_notes.dto.request.AuthRequest;
import com.bridgelabz.fundoo_notes.dto.response.AuthResponse;
import com.bridgelabz.fundoo_notes.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody AuthRequest request) {

        return ResponseEntity.ok(
                authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request) {

        return ResponseEntity.ok(
                authService.login(request));
    }
}
