package ru.otus.hw.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class BookDto {

    private final long id;

    private final String title;

    private final AuthorDto author;

    private final GenreDto genre;

    private final List<CommentDto> comments;

    public Book toDomain() {
        return new Book(id, title, author.toDomain(), genre.toDomain(),
                                            comments.stream().map(CommentDto::toDomain).collect(Collectors.toList()));
    }
}
