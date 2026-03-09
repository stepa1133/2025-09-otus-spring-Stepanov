package ru.otus.hw.config.books;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.authors.AuthorProcessor;
import ru.otus.hw.config.cache.IdMappingCache;
import ru.otus.hw.config.genres.GenreProcessor;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.mongo.MongoBook;

@RequiredArgsConstructor
@Component
public class BookProcessor implements ItemProcessor<Book, MongoBook> {

    private final IdMappingCache cache;

    private final AuthorProcessor authorProcessor;

    private final GenreProcessor genreProcessor;

    @Override
    public MongoBook process(@Nonnull Book item) throws Exception {
        String uuid = cache.getOrCreateBookUuid(item.getId());
        return new MongoBook(uuid,
                item.getTitle(), authorProcessor.process(item.getAuthor()), genreProcessor.process(item.getGenre()));

    }
}
