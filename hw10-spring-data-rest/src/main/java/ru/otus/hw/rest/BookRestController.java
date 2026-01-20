package ru.otus.hw.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.converters.dto.BookUpdateDto;
import ru.otus.hw.services.BookServiceImpl;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookServiceImpl bookService;

    @PutMapping("/book/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateDto book) {
        bookService.update(id, book.getTitle(), book.getAuthorId(), book.getGenreId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/book")
    public ResponseEntity<Void> insertBook(@Valid @RequestBody BookUpdateDto bookUpdateDto) {
        bookService.insert(bookUpdateDto.getTitle(), bookUpdateDto.getAuthorId(), bookUpdateDto.getGenreId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
