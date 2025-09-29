package ru.pro.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pro.model.dto.AuthenticationRequestDto;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

@RequestMapping("/api/auth")
public interface AuthApi {
    @PostMapping("/login")
    default ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto request) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @PostMapping("/logout")
    default ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }
}
