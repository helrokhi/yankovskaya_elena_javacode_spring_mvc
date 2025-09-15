package ru.pro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.pro.mapper.BookMapper;
import ru.pro.model.dto.BookDto;
import ru.pro.model.entity.BookEntity;
import ru.pro.repository.BookRepository;
import ru.pro.service.BookService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        Page<BookDto> page = bookRepository.findAll(pageable).map(bookMapper::toDto);
        log.info("Fetched users: {}", page.getContent());
        return page;
    }

    @Override
    public BookDto findById(UUID id) {
        BookEntity user = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
        return bookMapper.toDto(user);
    }

    @Override
    public BookDto create(BookDto dto) {
        BookEntity entity = bookMapper.toEntity(dto);
        BookEntity saved = bookRepository.save(entity);
        return bookMapper.toDto(saved);
    }

    @Override
    public BookDto update(UUID id, BookDto source) {
        BookEntity target = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        bookMapper.updateEntity(source, target);
        BookEntity saved = bookRepository.save(target);
        return bookMapper.toDto(saved);
    }

    @Override
    public void delete(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found: " + id);
        }
        bookRepository.deleteById(id);
    }
}
