package ru.otus.hw.config.authors;

import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.mongo.MongoAuthor;

@Component
public class AuthorProcessor implements ItemProcessor<Author, MongoAuthor> {
    @Override
    public MongoAuthor process(@Nonnull Author item) throws Exception {
        return new MongoAuthor(String.valueOf(item.getId()), item.getFullName());
    }
}
