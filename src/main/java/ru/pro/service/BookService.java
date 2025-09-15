package ru.pro.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.pro.model.dto.BookDto;

import java.util.UUID;

public interface BookService {
    Page<BookDto> findAll(Pageable pageable);

    BookDto findById(UUID id);

    BookDto create(BookDto dto);

    BookDto update(UUID id, BookDto dto);

    void delete(UUID id);
}
