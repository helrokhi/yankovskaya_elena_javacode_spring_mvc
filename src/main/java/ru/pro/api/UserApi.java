package ru.pro.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pro.model.dto.UserDto;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RequestMapping("/api/v1")
public interface UserApi {
    @RequestMapping(
            value = "/users",
            method = GET,
            produces = "application/json")
    default ResponseEntity<List<UserDto>> getAll() {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @RequestMapping(
            value = "/users/{id}",
            method = GET,
            produces = "application/json")
    default ResponseEntity<UserDto> getById(@PathVariable UUID id) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @RequestMapping(
            value = "/users",
            method = POST,
            consumes = "application/json",
            produces = "application/json")
    default ResponseEntity<UserDto> create(@RequestBody UserDto dto) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @RequestMapping(
            value = "/users/{id}",
            method = PUT,
            consumes = "application/json",
            produces = "application/json")
    default ResponseEntity<UserDto> update(
            @PathVariable UUID id,
            @RequestBody UserDto dto) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @RequestMapping(
            value = "/users/{id}",
            method = DELETE)
    default ResponseEntity<Void> delete(@PathVariable UUID id) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }
}
