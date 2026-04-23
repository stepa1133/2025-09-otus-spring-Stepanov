package ru.otus.hw.converters.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookDtoConverter {

    private final AuthorDtoConverter authorDtoConverter;

    private final GenreDtoConverter genreDtoConverter;

    private final CommentDtoConverter commentDtoConverter;

    public BookDto toDto(Book book) {

        if (book == null) {
            return null;
        }
        List<CommentDto> comments = book.getComments() == null ? Collections.emptyList() : book.getComments().stream()
                .map(commentDtoConverter::toDto)
                .toList();
        AuthorDto authorDto = authorDtoConverter.toDto(book.getAuthor());
        GenreDto genreDto = genreDtoConverter.toDto(book.getGenre());
        return new BookDto(book.getId(), book.getTitle(), authorDto, genreDto, comments);
    }

    public BookDto toDtoWithoutComments(Book book) {

        if (book == null) {
            return null;
        }
        AuthorDto authorDto = authorDtoConverter.toDto(book.getAuthor());
        GenreDto genreDto = genreDtoConverter.toDto(book.getGenre());
        return new BookDto(book.getId(), book.getTitle(), authorDto, genreDto, List.of());
    }

    public Book toDomain(BookDto bookDto) {
        List<Comment> comments = bookDto.getComments()
                .stream()
                .map(commentDtoConverter::toDomain)
                .toList();
        Author author = authorDtoConverter.toDomain(bookDto.getAuthor());
        Genre genre = genreDtoConverter.toDomain(bookDto.getGenre());
        return new Book(bookDto.getId(), bookDto.getTitle(), author, genre, comments);
    }
}
