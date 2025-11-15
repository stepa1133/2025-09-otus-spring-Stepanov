package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.CommentService;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    // find_bbi 1 +
    @ShellMethod(value = "Find all comments by book id", key = "find_bbi")
    public String findAllBookComments(long bookId) {
        return commentService.findAllBookComments(bookId).stream()
                .map(Comment::getCommentary)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // add_comment 1 "My favorite book"
    @ShellMethod(value = "Add comment", key = "add_comment")
    public String insertComment(long bookId, String commentary) {
        return commentService.insert(bookId, commentary).getCommentary();
    }

    // update_comment 2 4 "My second favorite book"
    @ShellMethod(value = "Update comment", key = "update_comment")
    public String updateComment(long id, long bookId, String commentary) {
        return commentService.update(id, bookId, commentary).getCommentary();
    }

    // delete_all_comments 1 +
    @ShellMethod(value = "Delete all book comments by id", key = "delete_all_comments")
    public void deleteAllCommentsByBookId(long bookId) {
        commentService.deleteAllCommentsByBookId(bookId);
    }

}
