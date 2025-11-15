package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(long id);

    List<Comment> findAllBookComments(long bookId);

    Comment insert(long bookId, String commentary);

    Comment update(long bookId, long id, String commentary);

    void deleteById(long id);

    void deleteAllCommentsByBookId(long bookId);

}
