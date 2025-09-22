package ru.pro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.pro.api.controller.BookController;
import ru.pro.model.dto.BookDto;
import ru.pro.service.BookService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
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

    private BookDto book;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public BookService bookService() {
            return Mockito.mock(BookService.class);
        }
    }

    @BeforeEach
    void setUp() {
        bookId = UUID.randomUUID();
        book = new BookDto(bookId, "1984", "George Orwell", 1949);
    }

    @Nested
    @DisplayName("GET /api/v1/books")
    class GetBooks {
        @DisplayName("возвращает список книг")
        void testGetAllBooks() throws Exception {
            when(bookService.findAll()).thenReturn(List.of(book));

            mockMvc.perform(get("/api/v1/books")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(book.id().toString()))
                    .andExpect(jsonPath("$[0].title").value("1984"))
                    .andExpect(jsonPath("$[0].author").value("George Orwell"));
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
            BookDto request = new BookDto(null, "1984", "George Orwell", 1949);
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
            BookDto request = new BookDto(bookId, "1984 Updated", "George Orwell", 1949);
            BookDto updated = new BookDto(bookId, "1984 Updated", "George Orwell", 1949);
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