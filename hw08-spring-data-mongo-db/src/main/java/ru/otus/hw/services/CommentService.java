package ru.otus.hw.services;

import ru.otus.hw.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<CommentDto> findById(String id);

    List<CommentDto> findAllBookComments(String bookId);

    CommentDto insert(String bookId, String commentary);

    CommentDto update(String bookId, String id, String commentary);

    void deleteById(String id);

    void deleteAllCommentsByBookId(String bookId);

}
