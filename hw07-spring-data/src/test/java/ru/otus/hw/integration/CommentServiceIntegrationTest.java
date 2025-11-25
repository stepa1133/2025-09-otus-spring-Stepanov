package ru.otus.hw.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.converters.dto.CommentDtoConverter;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.CommentService;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Тесты сервиса комментариев")
@DataJpaTest
@ComponentScan("ru.otus.hw")
@Transactional(propagation = Propagation.NEVER)

public class CommentServiceIntegrationTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentConverter commentConverter;

    @Autowired
    private CommentDtoConverter commentDtoConverter;


    @DisplayName("Не должен выбрасывать исключния при загрузке комментария по id")
    @Test
    void shouldDoNotThrowException1() {
        assertThatCode(
                () -> {
                    commentService.findById(1)
                            .map(commentDtoConverter::toDomain)
                            .map(commentConverter::commentToString)
                            .orElse("Comment with id %d not found".formatted(1));
                }).doesNotThrowAnyException();
    }

    @DisplayName("Не должен выбрасывать исключния при загрузке всех комментариев для книги")
    @Test
    void shouldDoNotThrowException2() {
        assertThatCode(
                () -> {
                    commentService.findAllBookComments(1).stream()
                            .map(commentDtoConverter::toDomain)
                            .map(commentConverter::commentToString)
                            .collect(Collectors.joining("," + System.lineSeparator()));
                }).doesNotThrowAnyException();
    }

    @DisplayName("Не должен выбрасывать исключния при добавлении комментария")
    @Test
    void shouldDoNotThrowException3() {
        assertThatCode(
                () -> {
                    Comment comment = commentDtoConverter.toDomain(commentService.insert(1, "kek"));
                    commentConverter.commentToString(comment);
                }).doesNotThrowAnyException();
    }

    @DisplayName("Не должен выбрасывать исключния при удалении комментария")
    @Test
    void shouldDoNotThrowException4() {
        assertThatCode(
                () -> {
                    commentService.deleteById(1);
                }).doesNotThrowAnyException();
    }

    @DisplayName("Не должен выбрасывать исключния при удалении всех комментариев для книги")
    @Test
    void shouldDoNotThrowException5() {
        assertThatCode(
                () -> {
                    commentService.deleteAllCommentsByBookId(1);
                }).doesNotThrowAnyException();
    }


}
