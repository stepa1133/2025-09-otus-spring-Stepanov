package ru.otus.hw.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.converters.dto.CommentUpdateDto;
import ru.otus.hw.services.CommentServiceImpl;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentServiceImpl commentService;

    @DeleteMapping("/book/{id}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") long bookId, @PathVariable long commentId) {
        commentService.deleteById(commentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/book/{id}/comment/")
    public ResponseEntity<Void> insertComment(@PathVariable("id") long bookId,
                                              @Valid @RequestBody CommentUpdateDto commentUpdateDto) {
        commentService.insert(bookId, commentUpdateDto.getCommentary());
        return ResponseEntity.ok().build();
    }
}
