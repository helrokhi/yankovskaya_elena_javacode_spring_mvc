package ru.pro.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.pro.model.entity.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import(BookRepository.class)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(UUID.randomUUID(), "Test Book", "Test Author", 2025);
        bookRepository.insert(book);
    }

    @Nested
    @DisplayName("findAll()")
    class FindAll {
        @Test
        @DisplayName("возвращает список всех книг")
        void testFindAll() {
            List<Book> books = bookRepository.findAll();
            assertThat(books).isNotEmpty();
            assertThat(books).extracting(Book::getTitle).contains("Test Book");
        }
    }

    @Nested
    @DisplayName("findById()")
    class FindById {
        @Test
        @DisplayName("возвращает книгу по существующему ID")
        void testFindByIdFound() {
            Optional<Book> found = bookRepository.findById(book.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getTitle()).isEqualTo("Test Book");
        }

        @Test
        @DisplayName("возвращает пустой Optional для несуществующего ID")
        void testFindByIdNotFound() {
            Optional<Book> found = bookRepository.findById(UUID.randomUUID());
            assertThat(found).isEmpty();
        }
    }

    @Nested
    @DisplayName("insert()")
    class Insert {
        @Test
        @DisplayName("сохраняет новую книгу")
        void testInsert() {
            Book newBook = new Book(UUID.randomUUID(), "Another Book", "Another Author", 2023);
            bookRepository.insert(newBook);

            Optional<Book> found = bookRepository.findById(newBook.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getTitle()).isEqualTo("Another Book");
        }
    }

    @Nested
    @DisplayName("update()")
    class Update {
        @Test
        @DisplayName("обновляет существующую книгу")
        void testUpdate() {
            book.setTitle("Updated Book");
            book.setAuthor("Updated Author");
            bookRepository.update(book);

            Book updated = bookRepository.findById(book.getId()).orElseThrow();
            assertThat(updated.getTitle()).isEqualTo("Updated Book");
            assertThat(updated.getAuthor()).isEqualTo("Updated Author");
        }
    }

    @Nested
    @DisplayName("delete()")
    class Delete {
        @Test
        @DisplayName("удаляет существующую книгу")
        void testDelete() {
            bookRepository.delete(book.getId());
            assertThat(bookRepository.findById(book.getId())).isEmpty();
        }

        @Test
        @DisplayName("выбрасывает исключение при удалении несуществующей книги")
        void testDeleteNotFound() {
            UUID randomId = UUID.randomUUID();
            assertThatThrownBy(() -> bookRepository.delete(randomId))
                    .isInstanceOf(EmptyResultDataAccessException.class);
        }
    }

    @Nested
    @DisplayName("existsById()")
    class ExistsById {
        @Test
        @DisplayName("возвращает true для существующей книги")
        void testExistsByIdTrue() {
            assertThat(bookRepository.existsById(book.getId())).isTrue();
        }

        @Test
        @DisplayName("возвращает false для несуществующей книги")
        void testExistsByIdFalse() {
            assertThat(bookRepository.existsById(UUID.randomUUID())).isFalse();
        }
    }
}