package com.bridgelabz.fundoo_notes.service.impl;

import com.bridgelabz.fundoo_notes.config.JwtService;
import com.bridgelabz.fundoo_notes.dto.request.AuthRequest;
import com.bridgelabz.fundoo_notes.dto.response.AuthResponse;
import com.bridgelabz.fundoo_notes.dto.response.UserResponse;
import com.bridgelabz.fundoo_notes.entity.User;
import com.bridgelabz.fundoo_notes.exception.ResourceNotFoundException;
import com.bridgelabz.fundoo_notes.repository.UserRepository;
import com.bridgelabz.fundoo_notes.service.AuthService;
import com.bridgelabz.fundoo_notes.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public AuthResponse register(AuthRequest request) {

        logger.info("Register request received for email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            logger.error("User already exists: {}", request.getEmail());

            return AuthResponse.builder()
                    .message("User already exists")
                    .build();
        }

        String token = UUID.randomUUID().toString();

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .verified(false)
                .verificationToken(token)
                .build();

        userRepository.save(user);

        emailService.sendVerificationEmail(
                user.getEmail(),
                token
        );

        logger.info("User registered successfully: {}", user.getEmail());

        return AuthResponse.builder()
                .message("User registered successfully. Please verify your email.")
                .build();
    }

    @Override
    public AuthResponse login(AuthRequest request) {

        logger.info("Login request received for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!user.isVerified()) {
            return AuthResponse.builder()
                    .message("Please verify your email first")
                    .build();
        }

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

        String jwtToken = jwtService.generateToken(user.getEmail());

        logger.info("User logged in successfully: {}", user.getEmail());

        return AuthResponse.builder()
                .message("Login successful")
                .token(jwtToken)
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

    @Override
    public String verifyEmail(String token) {

        User user = userRepository.findAll()
                .stream()
                .filter(u -> token.equals(u.getVerificationToken()))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid verification token"));

        user.setVerified(true);
        user.setVerificationToken(null);

        userRepository.save(user);

        logger.info("Email verified successfully for user: {}", user.getEmail());

        return "Email verified successfully";
    }
    @Override
    public String forgotPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        String token = UUID.randomUUID().toString();

        user.setVerificationToken(token);

        userRepository.save(user);

        emailService.sendForgotPasswordEmail(
                user.getEmail(),
                token
        );

        logger.info("Forgot password mail sent to: {}", user.getEmail());

        return "Password reset link sent to email";
    }
    @Override
    public String resetPassword(
            String token,
            String newPassword) {

        User user = userRepository.findAll()
                .stream()
                .filter(u -> token.equals(
                        u.getVerificationToken()))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid reset token"));

        user.setPassword(
                passwordEncoder.encode(newPassword));

        user.setVerificationToken(null);

        userRepository.save(user);

        logger.info("Password reset successfully for: {}", user.getEmail());

        return "Password reset successfully";
    }
}