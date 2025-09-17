package ru.pro.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pro.model.dto.UserDto;

import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

@RequestMapping("/api/auth")
public interface AuthApi {
    @PostMapping("/logout")
    default ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @GetMapping("/me")
    default ResponseEntity<UserDto> loadUser(OAuth2UserRequest userRequest) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }
}
