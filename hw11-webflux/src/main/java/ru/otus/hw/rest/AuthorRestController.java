package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.services.AuthorServiceImpl;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorServiceImpl authorService;

    @GetMapping("/api/authors") 
    public Flux<AuthorDto> getAllAuthors() {
        return authorService.findAll();
    }
}
