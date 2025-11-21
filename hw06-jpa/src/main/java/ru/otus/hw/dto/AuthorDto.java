package ru.otus.hw.dto;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.models.Author;

@RequiredArgsConstructor
public class AuthorDto {

    private final long id;

    private final String fullName;

    public Author toDomain() {
        return new Author(id, fullName);
    }
}
