package ru.otus.hw.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GenreDto {
    private final long id;

    private final String name;

}
