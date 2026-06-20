package com.bridgelabz.fundoo_notes.service;


import com.bridgelabz.fundoo_notes.dto.request.AuthRequest;
import com.bridgelabz.fundoo_notes.dto.response.AuthResponse;
import com.bridgelabz.fundoo_notes.dto.response.UserResponse;

public interface AuthService {

    AuthResponse register(AuthRequest request);

    AuthResponse login(AuthRequest request);

    UserResponse getLoggedInUser(String email);

    String verifyEmail(String token);

}
