package ru.pro.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pro.model.dto.BookDto;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RequestMapping("/api/v1")
public interface BookApi {
    @RequestMapping(
            value = "/books",
            method = GET,
            produces = "application/json")
    default ResponseEntity<List<BookDto>> getAll() {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @RequestMapping(
            value = "/books/{id}",
            method = GET,
            produces = "application/json")
    default ResponseEntity<BookDto> getById(@PathVariable UUID id) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @RequestMapping(
            value = "/books",
            method = POST,
            consumes = "application/json",
            produces = "application/json")
    default ResponseEntity<BookDto> create(@RequestBody BookDto dto) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @RequestMapping(
            value = "/books/{id}",
            method = PUT,
            consumes = "application/json",
            produces = "application/json")
    default ResponseEntity<BookDto> update(
            @PathVariable UUID id,
            @RequestBody BookDto dto) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @RequestMapping(
            value = "/books/{id}",
            method = DELETE)
    default ResponseEntity<Void> delete(@PathVariable UUID id) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }
}
