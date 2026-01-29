package ru.otus.hw.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Mono<CommentDto> findById(long id);

    Flux<CommentDto> findAllBookComments(long bookId);

    Mono<CommentDto> insert(long bookId, String commentary);

    Mono<CommentDto> update(long bookId, long id, String commentary);

    Mono<Void> deleteById(long id);

    void deleteAllCommentsByBookId(long bookId);

}
