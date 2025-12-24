package ru.otus.hw.services;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.converters.dto.BookDtoConverter;
import ru.otus.hw.models.Book;

import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest
@ComponentScan(basePackages = "ru.otus.hw")
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;


    @Autowired
    private BookDtoConverter converter;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        var allServiceBooks = bookService.findAll()
                .stream().map(bookDto -> converter.toDomain(bookDto)).toList();
        for (var serviceBook: allServiceBooks) {
            var id = serviceBook.getId();
            var actualBook = mongoTemplate.findById(id, Book.class);
            assertThat(actualBook).isNotNull()
                    .usingRecursiveComparison()
                    .ignoringFields("comments")
                    .ignoringExpectedNullFields()
                    .isEqualTo(serviceBook);
        }
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        bookService.insert("Тестовая книга", "1", "1");
        var book = mongoTemplate.findById("0", Book.class);
        assertThat(book.getTitle()).isEqualTo("Тестовая книга");
    }

    @DisplayName("должен изменять существующую книгу")
    @Test
    void shouldUpdateBook() {
        var standartBook = mongoTemplate.findById("1", Book.class);

        bookService.update(standartBook.getId(), "Тестовая книга",
                                                    standartBook.getAuthor().getId(), standartBook.getGenre().getId());
        var changedStandartBook = mongoTemplate.findById("1", Book.class);

        assertThat(standartBook).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("title")
                .ignoringExpectedNullFields()
                .isEqualTo(changedStandartBook);
        assertThat(changedStandartBook.getTitle()).isEqualTo("Тестовая книга");
    }

    @DisplayName("должен удалять книгу по id")
    @Test
    void shouldDeleteBook() {
        var bookBeforeDelete = bookService.findById("1");

        bookService.deleteById(bookBeforeDelete.get().getId());

        var bookAfterDelete = mongoTemplate.findById(bookBeforeDelete.get().getId(), Book.class);
        assertThat(bookAfterDelete).isEqualTo(null);
    }


}
