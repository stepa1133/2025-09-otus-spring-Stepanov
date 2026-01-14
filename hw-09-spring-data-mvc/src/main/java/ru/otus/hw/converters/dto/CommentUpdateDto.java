package ru.otus.hw.converters.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateDto {

    private Long id;

    private Long bookId;

    @NotBlank(message = "Комментарий для книги не может быть пустым")
    @Size(min = 1, max = 100, message = "Комментарий должен быть от 1 до 100 символов")
    private String commentary;

}
