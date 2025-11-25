package ru.otus.hw.converters.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Genre;

@Component
@RequiredArgsConstructor
public class GenreDtoConverter {
    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public Genre toDomain(GenreDto genreDto) {
        return new Genre(genreDto.getId(), genreDto.getName());
    }
}
