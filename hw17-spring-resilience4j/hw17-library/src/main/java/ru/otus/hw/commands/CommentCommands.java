package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
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
    public String findAllBookComments(long bookId) {
        return commentService.findAllBookComments(bookId).stream()
                .map(commentDtoConverter::toDomain)
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // find_comment 1
    @ShellMethod(value = "Find comment by id", key = "find_comment")
    public String findCommentById(long id) {
        return commentService.findById(id)
                .map(commentDtoConverter::toDomain)
                .map(commentConverter::commentToString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    // add_comment 1 "My favorite book"
    @ShellMethod(value = "Add comment", key = "add_comment")
    public String insertComment(long bookId, String commentary) {
        CommentDto commentDto = commentService.insert(bookId, commentary);
        Comment comment = commentDtoConverter.toDomain(commentDto);
        return commentConverter.commentToString(comment);
    }

    // update_comment 2 4 "My second favorite book"
    @ShellMethod(value = "Update comment", key = "update_comment")
    public String updateComment(long id, long bookId, String commentary) {
        CommentDto commentDto = commentService.update(id, bookId, commentary);
        Comment comment = commentDtoConverter.toDomain(commentDto);
        return commentConverter.commentToString(comment);
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
