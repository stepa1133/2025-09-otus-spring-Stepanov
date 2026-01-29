package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.converters.dto.CommentDtoConverter;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.CommentRepositoryCustom;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentDtoConverter commentDtoConverter;

    private final CommentRepositoryCustom commentRepositoryCustom;

    @Override
    @Transactional(readOnly = true)
    public Mono<CommentDto> findById(long id) {
        Mono<Comment> comment = commentRepository.findById(id);
        return comment.map(commentDtoConverter::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<CommentDto> findAllBookComments(long bookId) {
        return commentRepositoryCustom.findByBookId(bookId).map(commentDtoConverter::toDto);
    }

    @Override
    @Transactional
    public Mono<CommentDto> insert(long bookId, String commentary) {
        return save(0, bookId, commentary);
    }

    @Override
    @Transactional
    public Mono<CommentDto> update(long id, long bookId, String commentary) {
        return save(id, bookId, commentary);
    }

    @Override
    @Transactional
    public Mono<Void> deleteById(long id) {
        return commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllCommentsByBookId(long bookId) {
        commentRepository.deleteAllByBookId(bookId);
    }

    private Mono<CommentDto> save(long id, long bookId, String commentary) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Book with id %d not found".formatted(bookId))))
                .flatMap(book -> {
                    Comment comment = new Comment(id, book, commentary);
                    return commentRepository.save(comment);
                })
                .map(commentDtoConverter::toDto);
    }


}
