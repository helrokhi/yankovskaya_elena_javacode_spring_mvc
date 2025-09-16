package ru.pro.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.pro.mapper.BookMapper;
import ru.pro.model.dto.BookDto;
import ru.pro.model.entity.BookEntity;
import ru.pro.repository.BookRepository;
import ru.pro.service.impl.BookServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookEntity bookEntity;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bookEntity = new BookEntity();
        bookEntity.setId(UUID.randomUUID());
        bookEntity.setTitle("Test Book");

        bookDto = new BookDto(bookEntity.getId(), "Test Book", Set.of());
    }

    @Test
    @DisplayName("findAll() — возвращает страницу DTO")
    void testFindAll() {
        PageRequest pageable = PageRequest.of(0, 10);
        given(bookRepository.findAll(pageable)).willReturn(new PageImpl<>(List.of(bookEntity)));
        given(bookMapper.toDto(bookEntity)).willReturn(bookDto);

        Page<BookDto> result = bookService.findAll(pageable);

        assertThat(result.getContent()).containsExactly(bookDto);
        verify(bookRepository).findAll(pageable);
        verify(bookMapper).toDto(bookEntity);
    }

    @Test
    @DisplayName("findById() — успешный поиск")
    void testFindByIdFound() {
        given(bookRepository.findById(bookEntity.getId())).willReturn(Optional.of(bookEntity));
        given(bookMapper.toDto(bookEntity)).willReturn(bookDto);

        BookDto result = bookService.findById(bookEntity.getId());

        assertThat(result).isEqualTo(bookDto);
        verify(bookRepository).findById(bookEntity.getId());
        verify(bookMapper).toDto(bookEntity);
    }

    @Test
    @DisplayName("findById() — книга не найдена")
    void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();
        given(bookRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Book not found");

        verify(bookRepository).findById(id);
        verifyNoInteractions(bookMapper);
    }

    @Test
    @DisplayName("create() — сохраняет и возвращает DTO")
    void testCreate() {
        given(bookMapper.toEntity(bookDto)).willReturn(bookEntity);
        given(bookRepository.save(bookEntity)).willReturn(bookEntity);
        given(bookMapper.toDto(bookEntity)).willReturn(bookDto);

        BookDto result = bookService.create(bookDto);

        assertThat(result).isEqualTo(bookDto);
        verify(bookRepository).save(bookEntity);
    }

    @Test
    @DisplayName("update() — успешное обновление")
    void testUpdate() {
        UUID id = bookEntity.getId();
        given(bookRepository.findById(id)).willReturn(Optional.of(bookEntity));
        doAnswer(inv -> {
            BookDto source = inv.getArgument(0);
            BookEntity target = inv.getArgument(1);
            target.setTitle(source.title());
            return null;
        }).when(bookMapper).updateEntity(eq(bookDto), eq(bookEntity));
        given(bookRepository.save(bookEntity)).willReturn(bookEntity);
        given(bookMapper.toDto(bookEntity)).willReturn(bookDto);

        BookDto result = bookService.update(id, bookDto);

        assertThat(result).isEqualTo(bookDto);
        verify(bookRepository).findById(id);
        verify(bookRepository).save(bookEntity);
    }

    @Test
    @DisplayName("update() — книга не найдена")
    void testUpdateNotFound() {
        UUID id = UUID.randomUUID();
        given(bookRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.update(id, bookDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Book not found");

        verify(bookRepository).findById(id);
        verifyNoInteractions(bookMapper);
    }

    @Test
    @DisplayName("delete() — успешное удаление")
    void testDelete() {
        UUID id = bookEntity.getId();
        given(bookRepository.existsById(id)).willReturn(true);

        bookService.delete(id);

        verify(bookRepository).deleteById(id);
    }

    @Test
    @DisplayName("delete() — книга не найдена")
    void testDeleteNotFound() {
        UUID id = UUID.randomUUID();
        willThrow(new EmptyResultDataAccessException(1))
                .given(bookRepository).deleteById(id);

        assertThatThrownBy(() -> bookService.delete(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Book not found: " + id);

        verify(bookRepository).deleteById(id);
    }
}