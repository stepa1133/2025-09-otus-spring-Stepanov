package ru.otus.hw.config.books;

import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.authors.AuthorProcessor;
import ru.otus.hw.config.genres.GenreProcessor;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.mongo.MongoBook;

@Component
public class BookProcessor implements ItemProcessor<Book, MongoBook> {
    @Override
    public MongoBook process(@Nonnull Book item) throws Exception {
        GenreProcessor genreProcessor = new GenreProcessor();
        AuthorProcessor authorProcessor = new AuthorProcessor();
        return  new MongoBook(String.valueOf(item.getId()),
               item.getTitle(), authorProcessor.process(item.getAuthor()), genreProcessor.process(item.getGenre()));

    }
}
