package ru.otus.hw.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthorDto {

    private final long id;

    private final String fullName;

}
