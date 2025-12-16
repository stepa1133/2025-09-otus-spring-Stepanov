package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.converters.dto.GenreDtoConverter;
import ru.otus.hw.models.Genre;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan(basePackages = "ru.otus.hw")
@DataMongoTest

public class GenreServiceImplTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreDtoConverter converter;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("Загружать все жанры")
    @Test
    void findAllTest() {
        var genres = mongoTemplate.findAll(Genre.class)
                .stream().map(genre->converter.toDto(genre)).collect(Collectors.toList());
        var actualServiceGenres = genreService.findAll();

        assertThat(genres)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(actualServiceGenres);

    }

}
