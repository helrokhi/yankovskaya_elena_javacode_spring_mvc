package ru.pro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
public class JwtAuthenticationException extends AuthenticationException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpStatus httpStatus;

    public JwtAuthenticationException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public JwtAuthenticationException(String msg) {
        this(msg, UNAUTHORIZED);
    }
}
