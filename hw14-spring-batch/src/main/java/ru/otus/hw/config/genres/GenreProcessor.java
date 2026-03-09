package ru.otus.hw.config.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.cache.IdMappingCache;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.mongo.MongoGenre;

@RequiredArgsConstructor
@Component
public class GenreProcessor implements ItemProcessor<Genre, MongoGenre> {

    private final IdMappingCache cache;

    @Override
    public MongoGenre process(Genre item) throws Exception {
        String uuid = cache.getOrCreateGenreUuid(item.getId());
        return new MongoGenre(uuid, item.getName());
    }
}
