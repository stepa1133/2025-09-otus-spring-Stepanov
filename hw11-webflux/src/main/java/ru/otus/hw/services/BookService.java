package ru.otus.hw.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookDto;


public interface BookService {

    Mono<BookDto> findById(long id);

    Flux<BookDto> findAll();

    Mono<BookDto> insert(String title, long authorId, long genreId);

    Mono<BookDto> update(long id, String title, long authorId, long genreId);

    Mono<Void> deleteById(long id);
}
