package ru.pro.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.pro.model.dto.AuthenticationRequestDto;

public interface AuthService {
    String authenticate(AuthenticationRequestDto request, HttpServletResponse response);

    void logout(HttpServletRequest request, HttpServletResponse response);

    String refreshToken(HttpServletRequest request);
}
