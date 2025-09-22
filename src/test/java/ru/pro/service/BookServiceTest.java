package ru.pro.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.pro.mapper.BookMapper;
import ru.pro.model.dto.BookDto;
import ru.pro.model.entity.Book;
import ru.pro.repository.BookRepository;
import ru.pro.service.impl.BookServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
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

    private Book book;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublicationYear(2025);

        bookDto = new BookDto(book.getId(), book.getTitle(), book.getAuthor(),  book.getPublicationYear());
    }

    @Test
    @DisplayName("findAll() — возвращает список DTO")
    void testFindAll() {
        given(bookRepository.findAll()).willReturn(List.of(book));
        given(bookMapper.toDto(book)).willReturn(bookDto);

        List<BookDto> result = bookService.findAll();

        assertThat(result).containsExactly(bookDto);
        verify(bookRepository).findAll();
        verify(bookMapper).toDto(book);
    }

    @Test
    @DisplayName("findById() — успешный поиск")
    void testFindByIdFound() {
        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));
        given(bookMapper.toDto(book)).willReturn(bookDto);

        BookDto result = bookService.findById(book.getId());

        assertThat(result).isEqualTo(bookDto);
        verify(bookRepository).findById(book.getId());
        verify(bookMapper).toDto(book);
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
        given(bookMapper.toEntity(bookDto)).willReturn(book);
        willDoNothing().given(bookRepository).insert(book);
        given(bookMapper.toDto(book)).willReturn(bookDto);

        BookDto result = bookService.create(bookDto);

        assertThat(result).isEqualTo(bookDto);
        verify(bookMapper).toEntity(bookDto);
        verify(bookRepository).insert(book);
        verify(bookMapper).toDto(book);
    }

    @Test
    @DisplayName("update() — успешное обновление")
    void testUpdate() {
        UUID id = book.getId();
        given(bookRepository.findById(id)).willReturn(Optional.of(book));
        doAnswer(inv -> {
            BookDto source = inv.getArgument(0);
            Book target = inv.getArgument(1);
            target.setTitle(source.title());
            return null;
        }).when(bookMapper).updateEntity(eq(bookDto), eq(book));
        willDoNothing().given(bookRepository).update(book);
        given(bookMapper.toDto(book)).willReturn(bookDto);

        BookDto result = bookService.update(id, bookDto);

        assertThat(result).isEqualTo(bookDto);
        verify(bookRepository).findById(id);
        verify(bookMapper).updateEntity(bookDto, book);
        verify(bookRepository).update(book);
        verify(bookMapper).toDto(book);
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
        UUID id = book.getId();
        willDoNothing().given(bookRepository).delete(id);

        bookService.delete(id);

        verify(bookRepository).delete(id);
    }

    @Test
    @DisplayName("delete() — книга не найдена")
    void testDeleteNotFound() {
        UUID id = UUID.randomUUID();
        willThrow(new EmptyResultDataAccessException(1))
                .given(bookRepository).delete(id);

        assertThatThrownBy(() -> bookService.delete(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Book not found");

        verify(bookRepository).delete(id);
    }
}