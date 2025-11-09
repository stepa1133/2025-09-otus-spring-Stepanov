package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Optional<Book> findById(long id) {
        String sql = """
        SELECT books.id AS book_id, books.title, 
               authors.id AS author_id, authors.full_name, 
               genres.id AS genre_id, genres.name
        FROM books 
        JOIN authors ON books.author_id = authors.id
        JOIN genres ON books.genre_id = genres.id
        WHERE books.id = :id
        """;
        Book book = namedParameterJdbcOperations.queryForObject(sql, Map.of("id", id), new BookRowMapper());
        return Optional.of(book);
    }

    @Override
    public List<Book> findAll() {
        String sql = """
        SELECT books.id AS book_id, books.title, 
               authors.id AS author_id, authors.full_name, 
               genres.id AS genre_id, genres.name
        FROM books 
        JOIN authors ON books.author_id = authors.id
        JOIN genres ON books.genre_id = genres.id
        """;
        return namedParameterJdbcOperations.query(sql, new BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM books where id=:id";
        namedParameterJdbcOperations.update(sql, Map.of("id", id));
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValues(Map.of("title", book.getTitle(),
                "author_id",book.getAuthor().getId(),
                "genre_id", book.getGenre().getId()));

        String sql = "INSERT INTO books (title, author_id, genre_id) VALUES (:title, :author_id, :genre_id)";
        namedParameterJdbcOperations.update(sql, params, keyHolder, new String[]{"id"});
        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValues(Map.of("id", book.getId(),
                "title", book.getTitle(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId()));
        String sql = "UPDATE books SET title = :title, author_id = :author_id, genre_id = :genre_id WHERE id = :id";
        int rowsUpdated = namedParameterJdbcOperations.update(sql, params);
        if (rowsUpdated == 0) {
            throw new EntityNotFoundException("Book with id=" + book.getId() + " not found");
        }
        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            Author author = new Author();
            author.setId(rs.getLong("author_id"));
            author.setFullName(rs.getString("full_name"));

            Genre genre = new Genre();
            genre.setId(rs.getLong("genre_id"));
            genre.setName(rs.getString("name"));

            Book book = new Book();
            book.setId(rs.getLong("book_id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(author);
            book.setGenre(genre);

            return book;
        }
    }
}
