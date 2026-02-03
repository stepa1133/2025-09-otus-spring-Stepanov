package ru.otus.hw.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.converters.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;

import ru.otus.hw.services.BookServiceImpl;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookServiceImpl bookService;

    @GetMapping("/api/books")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/api/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BookDto> getBookById(@PathVariable("id") long id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBook(@PathVariable("id") long id) {
        return bookService.deleteById(id);
    }


    @PutMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BookDto> updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateDto book) {
        return bookService.update(id, book.getTitle(), book.getAuthorId(), book.getGenreId());
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookDto> insertBook(@Valid @RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.insert(bookUpdateDto.getTitle(), bookUpdateDto.getAuthorId(), bookUpdateDto.getGenreId());
    }


}
