package ru.otus.hw.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import ru.otus.hw.converters.dto.BookUpdateDto;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookServiceImpl bookService;

    private final AuthorServiceImpl authorService;

    private final GenreServiceImpl genreService;

    @PutMapping("/book/{id}")//+ нужна валидация
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateDto book) {
        bookService.update(id, book.getTitle(), book.getAuthorId(), book.getGenreId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/book")//+
    public ResponseEntity<Void> insertBook(@Valid @RequestBody BookUpdateDto bookUpdateDto) {
        bookService.insert(bookUpdateDto.getTitle(), bookUpdateDto.getAuthorId(), bookUpdateDto.getGenreId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/book/{id}")//+  нужна валидация
    public ResponseEntity<Void> deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
