package ru.pro.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pro.model.dto.AuthLogDto;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

@RequestMapping("/api/v1/auth")
public interface AuthLogApi {
    @GetMapping("/logs")
    default ResponseEntity<List<AuthLogDto>> findAll() {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }
}
