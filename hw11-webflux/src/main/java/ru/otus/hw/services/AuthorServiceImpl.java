package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import ru.otus.hw.converters.dto.AuthorDtoConverter;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.repositories.AuthorRepository;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorDtoConverter converter;

    @Override
    @Transactional(readOnly = true)
    public Flux<AuthorDto> findAll() {
        return authorRepository.findAll()
                .map(converter::toDto);
    }
}
