package ru.otus.hw.converters.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

@Component
@RequiredArgsConstructor
public class BookDtoConverter {
    private final AuthorDtoConverter authorDtoConverter;

    private final GenreDtoConverter genreDtoConverter;

    public BookDto toDto(Book book) {

        if (book == null) {
            return null;
        }
        AuthorDto authorDto = authorDtoConverter.toDto(book.getAuthor());
        GenreDto genreDto = genreDtoConverter.toDto(book.getGenre());
        return new BookDto(book.getId(), book.getTitle(), authorDto, genreDto);
    }

    public BookDto toDtoWithoutComments(Book book) {

        if (book == null) {
            return null;
        }
        AuthorDto authorDto = authorDtoConverter.toDto(book.getAuthor());
        GenreDto genreDto = genreDtoConverter.toDto(book.getGenre());
        return new BookDto(book.getId(), book.getTitle(), authorDto, genreDto);
    }

    public Book toDomain(BookDto bookDto) {
        Author author = authorDtoConverter.toDomain(bookDto.getAuthor());
        Genre genre = genreDtoConverter.toDomain(bookDto.getGenre());
        return new Book(bookDto.getId(), bookDto.getTitle(), author, genre);
    }
}
