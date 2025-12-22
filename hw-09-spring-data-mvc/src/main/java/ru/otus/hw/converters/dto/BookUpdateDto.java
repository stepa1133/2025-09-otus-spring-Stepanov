package ru.otus.hw.converters.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookUpdateDto {
    @NotNull
    private Long id;

    @NotBlank(message = "Book title can't be null")
    @Size(min = 1, max = 100, message = "Book title should be with size from 1 to 100 symbols")
    private String title;


//    @NotNull(message = "Author id is can't be null")

    @NotNull(message = "Книжечка не может быть без автора")
    private Long authorId;

//    @NotNull(message = "Genre id is can't be null")
    @NotNull(message = "Книжечка не может быть без жанра")
    private Long genreId;


}
