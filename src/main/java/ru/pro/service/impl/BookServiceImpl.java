package ru.pro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.pro.mapper.BookMapper;
import ru.pro.model.dto.BookDto;
import ru.pro.model.entity.Book;
import ru.pro.repository.BookRepository;
import ru.pro.service.BookService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> result = books.stream()
                .map(bookMapper::toDto)
                .toList();
        log.info("Fetched {} books", result.size());
        return result;
    }

    @Override
    public BookDto findById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto create(BookDto dto) {
        Book entity = bookMapper.toEntity(dto);

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        bookRepository.insert(entity);
        log.info("Created book: {}", entity);
        return bookMapper.toDto(entity);
    }

    @Override
    public BookDto update(UUID id, BookDto source) {
        Book target = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));

        bookMapper.updateEntity(source, target);

        bookRepository.update(target);
        log.info("Updated book with id={}", id);
        return bookMapper.toDto(target);
    }

    @Override
    public void delete(UUID id) {
        try {
            bookRepository.delete(id);
            log.info("Deleted book with id={}", id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Book not found: " + id);
        }
    }
}
