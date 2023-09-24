package com.bootcamp.posapi.service;

import com.bootcamp.posapi.model.AuthRequest;
import com.bootcamp.posapi.model.AuthResponse;
import com.bootcamp.posapi.model.ProfileResponse;
import com.bootcamp.posapi.model.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public interface AuthService {
    Optional<AuthResponse> authenticate(AuthRequest request);
    Optional<AuthResponse> register(RegisterRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
    Optional<ProfileResponse> profile(HttpServletRequest request, HttpServletResponse response);

}
