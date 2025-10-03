package ru.pro.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pro.model.dto.UserDto;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

@RequestMapping("/api/auth")
public interface AuthApi {
    @PostMapping("/logout")
    default ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @GetMapping("/me")
    default ResponseEntity<UserDto> loadUser(Authentication authentication) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @PutMapping("/users/{login}/unlock")
    default ResponseEntity<Void> unlockUser(@PathVariable String login) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }
}
