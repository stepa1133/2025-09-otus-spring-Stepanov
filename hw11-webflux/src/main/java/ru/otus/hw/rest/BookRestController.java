package ru.otus.hw.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.converters.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookServiceImpl;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookServiceImpl bookService;

    @GetMapping("/api/books") //+
    public Flux<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @DeleteMapping("/book/{id}") //+
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable("id") long id) {
        return bookService.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }


    @PutMapping("/book/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateDto book) {
        bookService.update(id, book.getTitle(), book.getAuthorId(), book.getGenreId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/book")
    public Mono<ResponseEntity<BookDto>> insertBook(@Valid @RequestBody BookUpdateDto bookUpdateDto) {
        Mono<BookDto> bookMono =
                bookService.insert(bookUpdateDto.getTitle(), bookUpdateDto.getAuthorId(), bookUpdateDto.getGenreId());
        return bookMono.map(ResponseEntity::ok);
    }


}
