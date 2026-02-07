package ru.otus.hw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.converters.dto.BookUpdateDto;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.rest.BookRestController;
import ru.otus.hw.services.BookServiceImpl;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@WebFluxTest({BookRestController.class})
public class BookRestControllerTest {
    @MockitoBean
    private BookServiceImpl bookService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnAllBooks() {
        when(bookService.findAll()).thenReturn(getDbBooksFlux());

        webTestClient.get().uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .hasSize(2)
                .value(books-> {
                    assertEquals("Gold egg", books.get(0).getTitle());
                    assertEquals("Shotgun", books.get(1).getTitle());
                });

    }

    @Test
    void shouldReturnBookById() {
        when(bookService.findById(1)).thenReturn(Mono.just(getDbBooks().get(0)));

        webTestClient.get().uri("/api/books/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(book-> assertEquals("Gold egg", book.getTitle()));
    }
    @Test
    void shouldDeleteBookById() {
        when(bookService.deleteById(1L)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/book/{id}", 1L)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        verify(bookService).deleteById(1L);
    }
    @Test
    void shouldUpdateBook() {
        BookUpdateDto request = new BookUpdateDto(1L, "Nice book!", 1L, 1L);

        BookDto response = new BookDto(1L,  "Nice book!", null, null, null);

        when(bookService.update(response.getId(), request.getTitle(), request.getAuthorId(), request.getGenreId()))
                .thenReturn(Mono.just(response));

        webTestClient.put()
                .uri("/book/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(dto -> assertEquals("Nice book!", dto.getTitle()));
    }

    @Test
    void shouldInsertNewBook() {
        BookUpdateDto request = new BookUpdateDto(1L, "Nice book!", 1L, 1L);

        BookDto response = new BookDto(1L,  "Nice book!", null, null, null);

        when(bookService.insert(request.getTitle(), request.getAuthorId(), request.getGenreId()))
                .thenReturn(Mono.just(response));

        webTestClient.post()
                .uri("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(dto -> assertEquals("Nice book!", dto.getTitle()));
    }

    private Flux<BookDto> getDbBooksFlux() {
        return Flux.fromIterable(getDbBooks());
    }
    private List<BookDto> getDbBooks() {
        var book1 = new BookDto(1, "Gold egg", getDbAuthors().get(0), getDbGenres().get(0), null);
        var book2 = new BookDto(2, "Shotgun", getDbAuthors().get(1), getDbGenres().get(1), null);
        return List.of(book1, book2);
    }

    private List<AuthorDto> getDbAuthors() {
        var author1 = new AuthorDto(1, "Elena Vorobey");
        var author2 = new AuthorDto(2, "Kurt Cobain");
        return List.of(author1, author2);
    }

    private List<GenreDto> getDbGenres() {
        var genre1 = new GenreDto(1, "Comedy");
        var genre2 = new GenreDto(2, "Scary");
        return List.of(genre1, genre2);
    }
}
