package ru.otus.hw.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class BookDto {

    private final long id;

    private final String title;

    private final AuthorDto author;

    private final GenreDto genre;

    private final List<CommentDto> comments;

}
