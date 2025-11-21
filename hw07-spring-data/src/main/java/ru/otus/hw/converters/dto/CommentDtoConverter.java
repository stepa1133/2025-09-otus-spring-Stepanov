package ru.otus.hw.converters.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;

@Component
@RequiredArgsConstructor
public class CommentDtoConverter {
    public CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getBook(), comment.getCommentary());
    }
}
