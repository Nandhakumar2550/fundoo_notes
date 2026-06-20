package com.bridgelabz.fundoo_notes.service.impl;

import com.bridgelabz.fundoo_notes.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(
            String to,
            String token) {

        String verificationLink =
                "http://localhost:8080/api/auth/verify?token=" + token;

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Fundoo Notes Email Verification");
        message.setText(
                "Click below to verify your account:\n"
                        + verificationLink
        );

        mailSender.send(message);
    }
    @Override
    public void sendForgotPasswordEmail(
            String to,
            String token) {

        String resetLink =
                "http://localhost:8080/api/auth/reset-password?token=" + token;

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Fundoo Notes Reset Password");
        message.setText(
                "Click below to reset your password:\n"
                        + resetLink
        );

        mailSender.send(message);
    }
}