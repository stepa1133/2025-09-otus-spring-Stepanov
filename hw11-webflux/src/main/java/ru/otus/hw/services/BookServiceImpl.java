package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.converters.dto.BookDtoConverter;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.BookRepositoryCustom;
import ru.otus.hw.repositories.GenreRepository;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookRepositoryCustom bookRepositoryCustom;

    private final BookDtoConverter bookDtoConverter;

    @Override
    @Transactional(readOnly = true)
    public Mono<BookDto> findById(long id) {
        return bookRepository.findById(id).map(bookDtoConverter::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<BookDto> findAll() {
        return bookRepositoryCustom.findAll().map(bookDtoConverter::toDto);
    }

    @Override
    @Transactional
    public Mono<BookDto> insert(String title, long authorId, long genreId) {
        return save(0, title, authorId, genreId);
    }

    @Override
    @Transactional
    public Mono<BookDto> update(long id, String title, long authorId, long genreId) {
        return save(id, title, authorId, genreId);
    }

    @Override
    @Transactional
    public Mono<Void> deleteById(long id) {
        return bookRepository.deleteById(id);
    }

    private Mono<BookDto> save(long id, String title, long authorId, long genreId) {
        Mono<Author> authorMono = authorRepository.findById(authorId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(
                        "Author with id %d not found".formatted(authorId)
                )));

        Mono<Genre> genreMono = genreRepository.findById(genreId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(
                        "Genre with id %d not found".formatted(genreId)
                )));

        return Mono.zip(authorMono, genreMono) // ждём оба объекта
                .flatMap(tuple -> {
                    Author author = tuple.getT1();
                    Genre genre = tuple.getT2();
                    Book book = new Book(id, title, author, genre, null);
                    return bookRepository.save(book);
                })
                .map(bookDtoConverter::toDto); // преобразуем Book → BookDto
    }

}
