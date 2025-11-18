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
import ru.otus.hw.services.BookServiceImpl;

import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;


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
}
