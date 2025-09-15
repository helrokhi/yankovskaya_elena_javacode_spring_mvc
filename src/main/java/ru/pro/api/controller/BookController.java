package ru.pro.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.pro.api.BookApi;
import ru.pro.model.dto.BookDto;
import ru.pro.service.BookService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookController implements BookApi {
    private final BookService bookService;

    @Override
    public ResponseEntity<Page<BookDto>> getAll(
            @PageableDefault(size = 5, sort = "title", direction = Sort.Direction.ASC) //default page = 0
            Pageable pageable) {
        log.info("Page content: {}", bookService.findAll(pageable));
        return ResponseEntity.ok(bookService.findAll(pageable));
    }

    @Override
    public ResponseEntity<BookDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @Override
    public ResponseEntity<BookDto> create(@RequestBody BookDto dto) {
        return ResponseEntity.ok(bookService.create(dto));
    }

    @Override
    public ResponseEntity<BookDto> update(@PathVariable UUID id, @RequestBody BookDto dto) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
