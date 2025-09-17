package ru.pro.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import ru.pro.model.dto.UserDto;

public interface AuthService {
    void logout(HttpServletRequest request, HttpServletResponse response);

    UserDto loadUser(OAuth2UserRequest userRequest);
}
