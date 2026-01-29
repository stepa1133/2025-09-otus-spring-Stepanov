package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import reactor.core.publisher.Mono;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.converters.dto.CommentDtoConverter;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.CommentService;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    private final CommentDtoConverter commentDtoConverter;

    // find_comments 1
    @ShellMethod(value = "Find all comments by book id", key = "find_comments")
    public Mono<String> findAllBookComments(long bookId) {
        return commentService.findAllBookComments(bookId)
                .map(commentDtoConverter::toDomain)
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // find_comment 1
    @ShellMethod(value = "Find comment by id", key = "find_comment")
    public Mono<String> findCommentById(long id) {
        return commentService.findById(id)                     // Mono<CommentDto>
                .map(commentDtoConverter::toDomain)            // Mono<Comment>
                .map(commentConverter::commentToString)       // Mono<String>
                .defaultIfEmpty("Comment with id %d not found".formatted(id)); // если Mono пустой
    }


    // add_comment 1 "My favorite book"
    @ShellMethod(value = "Add comment", key = "add_comment")
    public Mono<String> insertComment(long bookId, String commentary) {
        Mono<CommentDto> commentDto = commentService.insert(bookId, commentary);
        return commentDto.map(commentDtoConverter::toDomain)
                .map(commentConverter::commentToString);
    }

    // update_comment 2 4 "My second favorite book"
    @ShellMethod(value = "Update comment", key = "update_comment")
    public Mono<String> updateComment(long id, long bookId, String commentary) {
        return commentService.update(id, bookId, commentary)
                .map(commentDtoConverter::toDomain)
                .map(commentConverter::commentToString);
    }

    // delete_comment 1
    @ShellMethod(value = "Delete book comment by id", key = "delete_comment")
    public void deleteCommentsById(long id) {
        commentService.deleteById(id);
    }

    // delete_all_comments 1
    @ShellMethod(value = "Delete all book comments by id", key = "delete_all_comments")
    public void deleteAllCommentsByBookId(long bookId) {
        commentService.deleteAllCommentsByBookId(bookId);
    }

}
