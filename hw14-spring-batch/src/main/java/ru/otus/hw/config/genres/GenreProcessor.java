package ru.otus.hw.config.genres;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.mongo.MongoGenre;

@Component
public class GenreProcessor implements ItemProcessor<Genre, MongoGenre> {
    @Override
    public MongoGenre process(Genre item) throws Exception {
        return new MongoGenre(String.valueOf(item.getId()), item.getName());
    }
}
