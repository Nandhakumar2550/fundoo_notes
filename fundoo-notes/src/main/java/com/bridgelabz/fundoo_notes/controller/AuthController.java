package com.bridgelabz.fundoo_notes.controller;


import com.bridgelabz.fundoo_notes.dto.request.AuthRequest;
import com.bridgelabz.fundoo_notes.dto.response.AuthResponse;
import com.bridgelabz.fundoo_notes.dto.response.UserResponse;
import com.bridgelabz.fundoo_notes.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.bridgelabz.fundoo_notes.dto.request.ForgotPasswordRequest;
import com.bridgelabz.fundoo_notes.dto.request.ResetPasswordRequest;

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
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getLoggedInUser(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                authService.getLoggedInUser(email)
        );
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(
            @RequestParam String token) {

        return ResponseEntity.ok(
                authService.verifyEmail(token)
        );
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        return ResponseEntity.ok(
                authService.forgotPassword(
                        request.getEmail()
                )
        );
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequest request) {

        return ResponseEntity.ok(
                authService.resetPassword(
                        request.getToken(),
                        request.getNewPassword()
                )
        );
    }
}
