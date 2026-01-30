package ru.otus.hw.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.converters.dto.CommentUpdateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentServiceImpl;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentServiceImpl commentService;

    @GetMapping("api/book/{id}/comment")
    public Flux<CommentDto> getCommentsBookListPage(@PathVariable("id") long bookId) {
        return commentService.findAllBookComments(bookId);

    }

    @DeleteMapping("/api/book/{id}/comment/{commentId}")
    public Mono<ResponseEntity<Void>> deleteComment(@PathVariable("id") long bookId, @PathVariable long commentId) {
        return commentService.deleteById(commentId).then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PostMapping("/book/{id}/comment/")
    public Mono<CommentDto> insertComment(@PathVariable("id") long bookId,
                                              @Valid @RequestBody CommentUpdateDto commentUpdateDto) {
        return commentService.insert(bookId, commentUpdateDto.getCommentary());
    }
}
