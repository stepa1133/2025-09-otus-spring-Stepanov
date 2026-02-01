package ru.otus.hw;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.converters.dto.CommentUpdateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.rest.CommentRestController;
import ru.otus.hw.services.CommentServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@WebFluxTest({ CommentRestController.class})
public class CommentRestControllerTest {

    @MockitoBean
    private CommentServiceImpl service;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    private Flux<CommentDto> comments = Flux.just(new CommentDto(1L, new Book(),"Good"),
            new CommentDto(2L, new Book(),"Bad"));

    @Test
    void shouldReturnAllComments() throws Exception {
        when(service.findAllBookComments(1)).thenReturn(comments);
        webTestClient.get()
                .uri("/api/book/1/comment")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDto.class)
                .hasSize(2)
                .value(comments -> {
                    assertEquals("Good", comments.get(0).getCommentary());
                    assertEquals("Bad", comments.get(1).getCommentary());
                });
    }
    @Test
    void shouldDeleteComment() {
        when(service.deleteById(10L)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/book/{id}/comment/{commentId}", 1L, 10L)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        verify(service).deleteById(10L);
    }

    @Test
    void shouldInsertComment() {
        CommentUpdateDto request = new CommentUpdateDto(1L, 1L,"Nice book!");

        CommentDto response = new CommentDto(1L, new Book(), "Nice book!");

        when(service.insert(1L, "Nice book!"))
                .thenReturn(Mono.just(response));

        webTestClient.post()
                .uri("/book/{id}/comment/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentDto.class)
                .value(dto -> assertEquals("Nice book!", dto.getCommentary()));
    }
}
