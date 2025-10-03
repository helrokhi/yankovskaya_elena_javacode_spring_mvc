package ru.pro.exception.handlers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.pro.exception.ApiException;
import ru.pro.exception.wrappers.ErrorResponse;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex) {

        Map<String, Object> details = new HashMap<>();

        if (ex instanceof MethodArgumentNotValidException manv) {
            // Ошибки DTO (@RequestBody)
            manv.getBindingResult().getFieldErrors()
                    .forEach(fe -> details.put(fe.getField(),
                            Map.of(
                                    "rejectedValue", Optional.ofNullable(fe.getRejectedValue()).orElse("null"),
                                    "message", Objects.requireNonNull(fe.getDefaultMessage())
                            )));
        } else if (ex instanceof ConstraintViolationException cve) {
            // Ошибки параметров метода (@PathVariable, @RequestParam)
            cve.getConstraintViolations().forEach(cv -> {
                String field = cv.getPropertyPath().toString();
                details.put(field,
                        Map.of(
                                "rejectedValue", cv.getInvalidValue(),
                                "message", cv.getMessage()
                        ));
            });
        }

        logAtLevel(BAD_REQUEST, "VALIDATION_ERROR", ex);

        ErrorResponse errorResponse = new ErrorResponse(
                BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                "Некорректный формат запроса",
                Map.copyOf(details),
                now()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // --- Обработка кастомных исключений ApiException ---
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        logAtLevel(HttpStatus.valueOf(ex.getStatusCode()), ex.getCode(), ex);
        return ResponseEntity.status(ex.getStatusCode())
                .body(new ErrorResponse(
                        ex.getStatusCode(),
                        ex.getCode(),
                        ex.getMessage(),
                        ex.getDetails(),
                        ex.getTimestamp()
                ));
    }

    // --- Обработка EntityNotFoundException ---
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        return handleError(NOT_FOUND, "ENTITY_NOT_FOUND", ex.getMessage(), ex);
    }

    // --- Обработка IllegalArgumentException ---
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return handleError(BAD_REQUEST, "INVALID_ARGUMENT", ex.getMessage(), ex);
    }

    // --- Обработка ошибок JSON и UUID ---
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleInvalidInput(Exception ex) {
        String code = ex instanceof MethodArgumentTypeMismatchException ? "INVALID_UUID" : "INVALID_JSON";
        String message = ex.getMessage();
        return handleError(BAD_REQUEST, code, message, ex);
    }

    // --- Обработка AccessDeniedException ---
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        logAtLevel(FORBIDDEN, "ACCESS_DENIED", ex);
        return handleError(FORBIDDEN, "ACCESS_DENIED", "Доступ к ресурсу запрещен", ex);
    }

    // --- Универсальный обработчик для остальных исключений ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
        logAtLevel(INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", ex);
        return handleError(
                INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "Произошла внутренняя ошибка. Попробуйте позже.", ex);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        HttpStatus status = ex instanceof LockedException ? FORBIDDEN : UNAUTHORIZED;
        return handleError(
                status,
                "AUTH_ERROR",
                ex.getMessage(),
                ex
        );
    }

    private ResponseEntity<ErrorResponse> handleError(
            HttpStatus status, String code, String message, Throwable ex) {
        log.warn(code, ex);
        return buildResponse(status.value(), code, message, Map.of());
    }

    private ResponseEntity<ErrorResponse> buildResponse(int statusCode,
                                                        String code,
                                                        String message,
                                                        Map<String, Object> details) {
        ErrorResponse error = new ErrorResponse(
                statusCode,
                code,
                message,
                details,
                now()
        );
        return ResponseEntity.status(statusCode).body(error);
    }

    private void logAtLevel(HttpStatus status, String code, Throwable ex) {
        if (status.is4xxClientError()) {
            log.warn("{}: {}", code, ex.getMessage());
        } else {
            log.error("{}: {}", code, ex.getMessage());
        }
    }

    private Timestamp now() {
        return Timestamp.from(Instant.now());
    }
}