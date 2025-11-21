package ru.otus.hw.dto;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.models.Genre;

@RequiredArgsConstructor
public class GenreDto {
    private final long id;

    private final String name;

    public Genre toDomain() {
        return new Genre(id, name);
    }
}
