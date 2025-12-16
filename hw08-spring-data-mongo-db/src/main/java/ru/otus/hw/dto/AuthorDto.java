package ru.otus.hw.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthorDto {

    private final String id;

    private final String fullName;

}
