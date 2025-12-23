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

    private Long id;

    @NotBlank(message = "Название книги не может быть пустым")
    @Size(min = 1, max = 100, message = "Название книги должно быть от 1 до 100 символов")
    private String title;


//    @NotNull(message = "Author id is can't be null")

    @NotNull(message = "Книжечка не может быть без автора")
    private Long authorId;

//    @NotNull(message = "Genre id is can't be null")
    @NotNull(message = "Книжечка не может быть без жанра")
    private Long genreId;


}
