package ru.otus.hw.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.models.Book;

@RequiredArgsConstructor
@Getter
public class CommentDto {
    private final String id;

    private final Book book;

    private final String commentary;

}
