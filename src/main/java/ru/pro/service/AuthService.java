package ru.pro.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.pro.model.dto.AuthenticationRequestDto;

public interface AuthService {
    String authenticate(AuthenticationRequestDto request);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
