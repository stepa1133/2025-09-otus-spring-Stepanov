package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreServiceImpl;

@RestController
@RequiredArgsConstructor
public class GenreRestController {

    private final GenreServiceImpl genreService;

    @GetMapping("/api/genres") 
    public Flux<GenreDto> getAllGenres() {
        return genreService.findAll();
    }
}
