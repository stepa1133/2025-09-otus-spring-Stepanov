package ru.otus.hw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.rest.AuthorRestController;
import ru.otus.hw.services.AuthorServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@WebFluxTest(AuthorRestController.class)
public class AuthorRestControllerTest {

    @MockitoBean
    private AuthorServiceImpl service;

    @Autowired
    private WebTestClient webTestClient;

    private Flux<AuthorDto> authors = Flux.just(new AuthorDto(1L, "Kristina Orbakayte"),
            new AuthorDto(1L, "Iosif Kobzon"));


    @Test
    void shouldReturnAllAuthors() throws Exception {

        when(service.findAll()).thenReturn(authors);
        webTestClient.get()
                .uri("/api/authors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class)
                .hasSize(2)
                .value(authors -> {
                    assertEquals("Kristina Orbakayte", authors.get(0).getFullName());
                    assertEquals("Iosif Kobzon", authors.get(1).getFullName());
                });

    }

}
