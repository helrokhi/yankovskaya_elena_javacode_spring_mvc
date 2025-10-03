package ru.pro.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import ru.pro.model.dto.UserDto;

public interface AuthService {
    void logout(HttpServletRequest request, HttpServletResponse response);

    UserDto loadUser(Authentication authentication);

    void unlockUser(String login);
}
