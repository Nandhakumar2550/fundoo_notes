package com.bridgelabz.fundoo_notes.service.impl;

import com.bridgelabz.fundoo_notes.config.JwtService;
import com.bridgelabz.fundoo_notes.dto.request.AuthRequest;
import com.bridgelabz.fundoo_notes.dto.response.AuthResponse;
import com.bridgelabz.fundoo_notes.dto.response.UserResponse;
import com.bridgelabz.fundoo_notes.entity.User;
import com.bridgelabz.fundoo_notes.exception.ResourceNotFoundException;
import com.bridgelabz.fundoo_notes.repository.UserRepository;
import com.bridgelabz.fundoo_notes.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(AuthRequest request) {

        logger.info("Register request received for email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            logger.error("User already exists: {}", request.getEmail());

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

        logger.info("User registered successfully: {}", user.getEmail());

        return AuthResponse.builder()
                .message("User registered successfully")
                .build();
    }

    @Override
    public AuthResponse login(AuthRequest request) {

        logger.info("Login request received for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        boolean validPassword =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword());

        if (!validPassword) {
            logger.error("Invalid login attempt for email: {}", request.getEmail());

            return AuthResponse.builder()
                    .message("Invalid password")
                    .build();
        }

        String token = jwtService.generateToken(user.getEmail());

        logger.info("User logged in successfully: {}", user.getEmail());

        return AuthResponse.builder()
                .message("Login successful")
                .token(token)
                .build();
    }

    @Override
    public UserResponse getLoggedInUser(String email) {

        logger.info("Fetching logged-in user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .verified(user.isVerified())
                .build();
    }
}