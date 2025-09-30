package ru.pro.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.pro.api.AuthApi;
import ru.pro.model.dto.AuthenticationRequestDto;
import ru.pro.service.AuthService;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;

    @Override
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequestDto request) {
        String token = authService.authenticate(request);
        Map<String, String> response = Map.of(
                "login", request.login(),
                "token", token
        );
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity.status(OK).body("Logged out successfully");
    }
}
