package ru.otus.hw.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookDto {

    private final String id;

    private final String title;

    private final AuthorDto author;

    private final GenreDto genre;


}
