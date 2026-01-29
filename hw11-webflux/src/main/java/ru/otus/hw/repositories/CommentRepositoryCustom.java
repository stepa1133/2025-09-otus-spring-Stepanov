package ru.otus.hw.repositories;

import io.r2dbc.spi.Readable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustom {


    private final R2dbcEntityTemplate template;

    private static final String SQL_ALL = """
          SELECT c.id, c.commentary, c.book_id, b.title, b.author_id, b.genre_id, a.full_name, g.name FROM comments as c
                JOIN books as b ON c.book_id = b.id
                JOIN authors as a on b.author_id = a.id
                 JOIN genres as g on b.genre_id = g.id
          WHERE b.id = $1
            """;


    public Flux<Comment> findByBookId(long id) {
        return template.getDatabaseClient().inConnectionMany(connection ->
                Flux.from(connection.createStatement(SQL_ALL)
                                .bind(0, id)
                                .execute())
                        .flatMap(result -> result.map(this::mapper)));
    }

    public Mono<Comment> save(Comment comment) {
        return template.getDatabaseClient()
                .sql("INSERT INTO comments (book_id, commentary) VALUES ($1, $2)")
                .bind(0, comment.getBook().getId())       // $1 → bookId
                .bind(1, comment.getCommentary())         // $2 → commentary
                .fetch()
                .rowsUpdated()                             // выполняем insert
                .thenReturn(comment);                      // возвращаем исходный объект
    }




    private Comment mapper(Readable selectedRecord) {
        try {
            Long id = selectedRecord.get(0, Long.class);
            String commentary = selectedRecord.get(1, String.class);

            Long bookId = selectedRecord.get(2, Long.class);
            String bookTitle = selectedRecord.get(3, String.class);

            Long authorId = selectedRecord.get(4, Long.class);
            String authorFullName = selectedRecord.get(6, String.class);

            Long genreId = selectedRecord.get(5, Long.class);
            String genreName = selectedRecord.get(7, String.class);

            Author author = new Author(authorId, authorFullName);
            Genre genre = new Genre(genreId, genreName);

            Book book = new Book(bookId, bookTitle, author, genre, null);

            return new Comment(id , book, commentary);
        } catch (Exception e) {
            throw new IllegalArgumentException("books:"  + " parsing error:" + e);
        }
    }
}
