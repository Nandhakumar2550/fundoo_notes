package com.bridgelabz.fundoo_notes.service;

public interface EmailService {

    void sendVerificationEmail(String to, String token);

    void sendForgotPasswordEmail(String to, String token);
}