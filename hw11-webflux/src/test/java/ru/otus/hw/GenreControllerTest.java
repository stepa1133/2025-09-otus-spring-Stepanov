package ru.otus.hw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Genre;
import ru.otus.hw.rest.GenreRestController;
import ru.otus.hw.services.GenreServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebFluxTest(GenreRestController.class)
public class GenreControllerTest {

    @MockitoBean
    private GenreServiceImpl service;

    @Autowired
    private WebTestClient webTestClient;


    private Flux<GenreDto> genres = Flux.just(new GenreDto(1L, "Adventure"),
            new GenreDto(1L, "Scary"));

    @Test
    void shouldReturnAllGenres() throws Exception {
        when(service.findAll()).thenReturn(genres);
        webTestClient.get()
                .uri("/api/genres")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Genre.class)
                .hasSize(2)
                .value(genres -> {
                    assertEquals("Adventure", genres.get(0).getName());
                    assertEquals("Scary", genres.get(1).getName());
                });
    }
}
