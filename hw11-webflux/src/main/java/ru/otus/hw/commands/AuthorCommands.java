package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import reactor.core.publisher.Mono;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.converters.dto.AuthorDtoConverter;
import ru.otus.hw.services.AuthorService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    private final AuthorDtoConverter authorDtoConverter;

    @ShellMethod(value = "Find all authors", key = "aa")
    public Mono<String> findAllAuthors() {
        return authorService.findAll()
                .map(authorDtoConverter::toDomain)
                .map(authorConverter::authorToString)
                .collectList()   // Flux<String> -> Mono<List<String>>
                .map(list -> String.join("," + System.lineSeparator(), list));
    }

}
