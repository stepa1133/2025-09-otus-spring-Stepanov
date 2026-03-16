package ru.otus.hw.config.authors;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.cache.IdMappingCache;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.mongo.MongoAuthor;

@RequiredArgsConstructor
@Component
public class AuthorProcessor implements ItemProcessor<Author, MongoAuthor> {

    private final IdMappingCache cache;

    @Override
    public MongoAuthor process(@Nonnull Author item) throws Exception {
        String uuid = cache.getOrCreateAuthorUuid(item.getId());
        return new MongoAuthor(uuid, item.getFullName());
    }
}
