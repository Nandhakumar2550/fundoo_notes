package com.bridgelabz.fundoo_notes.service;


import com.bridgelabz.fundoo_notes.dto.request.AuthRequest;
import com.bridgelabz.fundoo_notes.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(AuthRequest request);
}
