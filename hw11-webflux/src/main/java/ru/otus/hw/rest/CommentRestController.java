package ru.otus.hw.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.converters.dto.CommentUpdateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentServiceImpl;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentServiceImpl commentService;

    @GetMapping("api/book/{id}/comment") //+
    public Flux<CommentDto> getCommentsBookListPage(@PathVariable("id") long bookId) {
        return commentService.findAllBookComments(bookId);

    }

    @DeleteMapping("/api/book/{id}/comment/{commentId}") //+
    public Mono<ResponseEntity<Void>> deleteComment(@PathVariable("id") long bookId, @PathVariable long commentId) {
        return commentService.deleteById(commentId).then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PostMapping("/book/{id}/comment/")
    public ResponseEntity<Void> insertComment(@PathVariable("id") long bookId,
                                              @Valid @RequestBody CommentUpdateDto commentUpdateDto) {
        commentService.insert(bookId, commentUpdateDto.getCommentary());
        return ResponseEntity.ok().build();
    }
}
