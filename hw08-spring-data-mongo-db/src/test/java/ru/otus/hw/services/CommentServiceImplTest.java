package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.hw.converters.dto.CommentDtoConverter;
import ru.otus.hw.models.Comment;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ComponentScan(basePackages = "ru.otus.hw")
public class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentDtoConverter converter;


    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnCommentById() {
        var serviceComment = converter.toDomain(commentService.findById("1").get());
        var templateComment = mongoTemplate.findById("1", Comment.class);
        assertThat(serviceComment).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("book")
                .ignoringExpectedNullFields()
                .isEqualTo(templateComment);
    }

    @DisplayName("должен загружать все комментарии книги по id книги")
    @Test
    void shouldReturnAllBookCommentsByBookId() {
        var query = new Query();
        query.addCriteria(Criteria.where("book.id").is("1"));
        var templateBooksComment = mongoTemplate.find(query, Comment.class);
        var serviceBookComments = commentService.findAllBookComments("1")
                                                .stream().map(t->converter.toDomain(t)).collect(Collectors.toList());
        assertThat(templateBooksComment)
                .usingRecursiveComparison()
                .ignoringFields("book")
                .ignoringExpectedNullFields()
                .isEqualTo(serviceBookComments);
   }

    @DisplayName("должен проверять сохранение нового комментария для книги")
    @Test
    void shouldSaveNewComment() {
        commentService.insert("2", "new Comment");
        var query = new Query();
        query.addCriteria(Criteria.where("book.id").is("2"));
        query.addCriteria(Criteria.where("commentary").is("new Comment"));
        var templateComment = mongoTemplate.find(query, Comment.class);
        assertThat(templateComment)
                .extracting("commentary")
                .contains("new Comment");
    }

    @DisplayName("должен удалять комментарий по id")
    @Test
    void shouldDeleteBook() {
        var doNotShouldBeNull = mongoTemplate.findById("1", Comment.class);
        assertThat(doNotShouldBeNull).isNotNull();

        commentService.deleteById("1");

        var shouldBeNull = mongoTemplate.findById("1", Comment.class);
        assertThat(shouldBeNull).isNull();

    }

    @DisplayName("должен удалять все комментарии для определенной книги")
    @Test
    void shouldDeleteAllBookComments() {
        var serviceBookComments = commentService.findAllBookComments("1")
                .stream().map(t->converter.toDomain(t)).collect(Collectors.toList());
        assertThat(serviceBookComments).isNotEmpty();

        commentService.deleteAllCommentsByBookId("1");

        var query = new Query();
        query.addCriteria(Criteria.where("book.id").is("1"));
        var serviceBookCommentsAfterDelete = mongoTemplate.find(query, Comment.class);

        assertThat(serviceBookCommentsAfterDelete).isEmpty();
    }

}
