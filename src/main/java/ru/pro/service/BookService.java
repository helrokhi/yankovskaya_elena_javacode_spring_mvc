package ru.pro.service;

import ru.pro.model.dto.BookDto;

import java.util.List;
import java.util.UUID;

public interface BookService {
    List<BookDto> findAll();

    BookDto findById(UUID id);

    BookDto create(BookDto dto);

    BookDto update(UUID id, BookDto dto);

    void delete(UUID id);
}
