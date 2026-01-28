package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.dto.CommentDtoConverter;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentDtoConverter commentDtoConverter;

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDto> findById(long id) {
        Comment comment = commentRepository.findById(id).get();
        return Optional.ofNullable(commentDtoConverter.toDto(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAllBookComments(long bookId) {
        return commentRepository.findByBookId(bookId)
                .stream()
                .map(commentDtoConverter::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CommentDto insert(long bookId, String commentary) {
        return save(0, bookId, commentary);
    }

    @Override
    @Transactional
    public CommentDto update(long id, long bookId, String commentary) {
        return save(id, bookId, commentary);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllCommentsByBookId(long bookId) {
        commentRepository.deleteAllByBookId(bookId);
    }

    private CommentDto save(long id, long bookId, String commentary) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        var comment = new Comment(id, book, commentary);
        return commentDtoConverter.toDto(commentRepository.save(comment));
    }

}
