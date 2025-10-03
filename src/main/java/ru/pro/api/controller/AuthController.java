package ru.pro.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.pro.api.AuthApi;
import ru.pro.model.dto.UserDto;
import ru.pro.service.AuthService;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;

    @Override
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity.status(OK).body("Logged out successfully");
    }

    @Override
    public ResponseEntity<UserDto> loadUser(Authentication authentication) {
        return ResponseEntity.ok(authService.loadUser(authentication));
    }

    @Override
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<Void> unlockUser(@PathVariable String login) {
        authService.unlockUser(login);
        return ResponseEntity.ok().build();
    }
}
