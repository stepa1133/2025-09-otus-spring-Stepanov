package ru.otus.hw.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@DisplayName("Тесты сервиса книг")
@DataJpaTest
@ComponentScan("ru.otus.hw")
@Transactional(propagation = Propagation.NEVER)
public class BookServiceIntegrationTest {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private BookConverter bookConverter;


    @DisplayName("Не должен выбрасывать исключния при загрузке всех книг")
    @Test
    void shouldDoNotThrowException1() {
        assertThatCode(
                () -> bookService.findAll().stream()
                        .map(BookDto::toDomain)
                        .map(bookConverter::bookToString)
                        .collect(Collectors.joining("," + System.lineSeparator())))
        .doesNotThrowAnyException();
    }

    @DisplayName("Не должен выбрасывать исключния при загрузке книги по итендификатору")
    @Test
    void shouldDoNotThrowException2() {
        assertThatCode(
                () -> bookService.findById(1)
                        .map(BookDto::toDomain)
                        .map(bookConverter::bookToString))
                .doesNotThrowAnyException();
    }

    @DisplayName("Не должен выбрасывать исключния при вставке новой книги")
    @Test
    void shouldDoNotThrowException3() {
        assertThatCode(
                () -> {
                    var savedBook = bookService.insert("title", 1, 1);
                    bookConverter.bookToString(savedBook.toDomain());
                }
        ).doesNotThrowAnyException();
    }

    @DisplayName("Не должен выбрасывать исключния при обновлении книги")
    @Test
    void shouldDoNotThrowException4() {
        assertThatCode(
                () -> {
                    var savedBook = bookService.update(1, "title", 1, 1);
                    bookConverter.bookToString(savedBook.toDomain());
                }
        ).doesNotThrowAnyException();
    }

    @DisplayName("Не должен выбрасывать исключния при удалении книги")
    @Test
    void shouldDoNotThrowException5() {
        assertThatCode(
                () -> bookService.deleteById(1)
        ).doesNotThrowAnyException();
    }

    @DisplayName("Должен найти книгу по id")
    @Test
    void shouldFindBookById() {
        Optional<BookDto> book = bookService.findById(1);

        assertThat(book)
                .isPresent()
                .get()
                .extracting(BookDto::getId)
                .isEqualTo(1L);
    }

    @DisplayName("Должен выгружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        List<BookDto> books = bookService.findAll();

        assertThat(books)
                .extracting(BookDto::getId)
                .containsExactlyInAnyOrder(1L, 2L, 3L);

    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        bookService.insert("Как учить студентов и не поехать кукухой", 1, 1);
        Optional<BookDto> book = bookService.findById(4);
        assertThat(book)
                .isPresent()
                .get()
                .extracting(BookDto::getTitle)
                .isEqualTo("Как учить студентов и не поехать кукухой");
    }

    @DisplayName("должен обновлять книгу")
    @Test
    void shouldUpdateBook() {
        Optional<BookDto> bookDto = bookService.findById(1);
        assertThat(bookDto)
                .isPresent()
                .get()
                .extracting(BookDto::getTitle)
                .isNotEqualTo("Как учить студентов и не поехать кукухой");
        Book book = bookDto.get().toDomain();



        bookService.update(book.getId(), "Как учить студентов и не поехать кукухой", book.getAuthor().getId(), book.getGenre().getId());

        Optional<BookDto> bookDtoAfter = bookService.findById(1);
        assertThat(bookDtoAfter)
                .isPresent()
                .get()
                .extracting(BookDto::getTitle)
                .isEqualTo("Как учить студентов и не поехать кукухой");
    }

    @DisplayName("должен удалять книгу")
    @Test
    void shouldDeleteBook() {
        Optional<BookDto> existingBook = bookService.findById(1);
        assertThat(existingBook).isPresent();

        bookService.deleteById(1);

        assertThatThrownBy(
                () -> bookService.findById(1)
        );

    }
}
