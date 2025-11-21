package ru.otus.hw.dto;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

@RequiredArgsConstructor
public class CommentDto {
    private final long id;

    private final Book book;

    private final String commentary;

    public Comment toDomain() {
        return new Comment(id, book, commentary);
    }

}
