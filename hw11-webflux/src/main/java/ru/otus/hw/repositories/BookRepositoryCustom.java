package ru.otus.hw.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.Readable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustom {

    private final R2dbcEntityTemplate template;

    private static final String SQL_ALL = """
          SELECT * FROM books as b
                JOIN authors as a ON b.author_id = a.id
                JOIN genres as g ON b.genre_id = g.id
            """;


    public Flux<Book> findAll() {
        return template.getDatabaseClient().inConnectionMany(connection ->
                Flux.from(connection.createStatement(SQL_ALL)
                                .execute())
                        .flatMap(result -> result.map(this::mapper)));
    }

    public Mono<Book> findById(Long id) {
        return template.getDatabaseClient()
                .sql(SQL_ALL + " WHERE b.id = :id")
                .bind("id", id)
                .map(this::mapper)
                .one();
    }

    public Mono<Book> save(Book book) {
        return template.getDatabaseClient()
                .sql("""
                UPDATE books
                SET title = $2,
                    author_id = $3,
                    genre_id = $4
                WHERE id = $1
            """)
                .bind(0, book.getId())
                .bind(1, book.getTitle())
                .bind(2, book.getAuthor().getId())
                .bind(3, book.getGenre().getId())
                .fetch()
                .rowsUpdated()
                .flatMap(rows ->
                        rows == 0
                                ? Mono.error(new EntityNotFoundException("Book not found"))
                                : Mono.just(book)
                );
    }
    private Book mapper(Readable selectedRecord) {
        try {
            Long id = selectedRecord.get(0, Long.class);
            String title = selectedRecord.get(1, String.class);

            Long authorId = selectedRecord.get(2, Long.class);
            String authorFullName =  selectedRecord.get(5, String.class);

            Long genreId = selectedRecord.get(3, Long.class);
            String genreName =  selectedRecord.get(7, String.class);

            Author author = new Author(authorId, authorFullName);
            Genre genre = new Genre(genreId, genreName);

            return new Book(id, title, author, genre, null);
        } catch (Exception e) {
            throw new IllegalArgumentException("books:"  + " parsing error:" + e);
        }
    }
}
