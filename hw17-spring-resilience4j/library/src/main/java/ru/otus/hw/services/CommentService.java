package ru.otus.hw.services;

import ru.otus.hw.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<CommentDto> findById(long id);

    List<CommentDto> findAllBookComments(long bookId);

    CommentDto insert(long bookId, String commentary);

    CommentDto update(long bookId, long id, String commentary);

    void deleteById(long id);

    void deleteAllCommentsByBookId(long bookId);

}
