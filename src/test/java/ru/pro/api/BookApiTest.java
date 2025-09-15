package ru.pro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.pro.api.controller.BookController;
import ru.pro.model.dto.AuthorDto;
import ru.pro.model.dto.BookDto;
import ru.pro.service.BookService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(BookApiTest.TestConfig.class)
class BookApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookService bookService;

    private UUID bookId;
    private AuthorDto author;
    private BookDto book;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public BookService bookService() {
            return mock(BookService.class);
        }
    }

    @BeforeEach
    void setUp() {
        bookId = UUID.randomUUID();
        author = new AuthorDto(UUID.randomUUID(), "George", "Orwell");
        book = new BookDto(bookId, "1984", Set.of(author));
    }

    @Nested
    @DisplayName("GET /api/v1/books")
    class GetBooks {

        @Test
        @DisplayName("возвращает страницу книг")
        void testGetAllBooks() throws Exception {
            Page<BookDto> page = new PageImpl<>(List.of(book), PageRequest.of(0, 5), 1);
            when(bookService.findAll(any(Pageable.class))).thenReturn(page);

            mockMvc.perform(get("/api/v1/books")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].id").value(book.id().toString()))
                    .andExpect(jsonPath("$.content[0].title").value("1984"))
                    .andExpect(jsonPath("$.content[0].authors[0].firstName").value("George"));
        }

        @Test
        @DisplayName("возвращает книгу по ID")
        void testGetBookById() throws Exception {
            when(bookService.findById(bookId)).thenReturn(book);

            mockMvc.perform(get("/api/v1/books/{id}", bookId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(bookId.toString()))
                    .andExpect(jsonPath("$.title").value("1984"));
        }
    }

    @Nested
    @DisplayName("POST /api/v1/books")
    class CreateBook {

        @Test
        @DisplayName("создает книгу")
        void testCreateBook() throws Exception {
            BookDto request = new BookDto(null, "1984", Set.of(author));
            when(bookService.create(any(BookDto.class))).thenReturn(book);

            mockMvc.perform(post("/api/v1/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(book.id().toString()))
                    .andExpect(jsonPath("$.title").value("1984"));
        }
    }

    @Nested
    @DisplayName("PUT /api/v1/books/{id}")
    class UpdateBook {

        @Test
        @DisplayName("обновляет книгу")
        void testUpdateBook() throws Exception {
            BookDto request = new BookDto(bookId, "1984 Updated", Set.of(author));
            BookDto updated = new BookDto(bookId, "1984 Updated", Set.of(author));
            when(bookService.update(eq(bookId), any(BookDto.class))).thenReturn(updated);

            mockMvc.perform(put("/api/v1/books/{id}", bookId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value("1984 Updated"));
        }
    }

    @Nested
    @DisplayName("DELETE /api/v1/books/{id}")
    class DeleteBook {

        @Test
        @DisplayName("удаляет книгу")
        void testDeleteBook() throws Exception {
            doNothing().when(bookService).delete(bookId);

            mockMvc.perform(delete("/api/v1/books/{id}", bookId))
                    .andExpect(status().isNoContent());

            verify(bookService).delete(bookId);
        }
    }
}