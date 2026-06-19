package com.bridgelabz.fundoo_notes.service.impl;

import com.bridgelabz.fundoo_notes.dto.request.AuthRequest;
import com.bridgelabz.fundoo_notes.dto.response.AuthResponse;
import com.bridgelabz.fundoo_notes.entity.User;
import com.bridgelabz.fundoo_notes.repository.UserRepository;
import com.bridgelabz.fundoo_notes.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(AuthRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.builder()
                    .message("User already exists")
                    .build();
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .verified(false)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .message("User registered successfully")
                .build();
    }
}