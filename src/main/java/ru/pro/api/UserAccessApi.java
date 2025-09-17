package ru.pro.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

@RequestMapping("/api/v1/users")
public interface UserAccessApi {
    @PutMapping("/{login}/unlock")
    default ResponseEntity<Void> unlockAccount(@PathVariable String login) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }
}
