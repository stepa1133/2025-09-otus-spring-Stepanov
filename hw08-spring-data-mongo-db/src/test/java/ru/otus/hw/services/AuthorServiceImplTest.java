package ru.otus.hw.services;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.converters.dto.AuthorDtoConverter;
import ru.otus.hw.models.Author;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest
@Import({AuthorServiceImpl.class, AuthorDtoConverter.class})
public class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorDtoConverter converter;

    @Autowired
    private MongoTemplate mongoTemplate;



    @DisplayName("Загружать всех авторов")
    @Test
    void findAllTest() {
        var authors = mongoTemplate.findAll(Author.class)
                                         .stream().map(author->converter.toDto(author)).collect(Collectors.toList());
        var actualServiceAuthors = authorService.findAll();

        assertThat(authors)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(actualServiceAuthors);

    }
}

