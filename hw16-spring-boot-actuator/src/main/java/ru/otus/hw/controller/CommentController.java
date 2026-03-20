package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.converters.dto.CommentUpdateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @GetMapping("/book/{id}/comment")
    public String getCommentsBookListPage(@PathVariable("id") long bookId, Model model) {
        List<CommentDto> comments = commentService.findAllBookComments(bookId);
        model.addAttribute("comments", comments);
        model.addAttribute("bookId", bookId);
        return "commentsList";
    }

    @GetMapping("/book/{id}/comment/new")
    public String addCommentaryPage(@PathVariable("id") long bookId, Model model) {
        var commentUpdateDto = new CommentUpdateDto();
        commentUpdateDto.setBookId(bookId);
        model.addAttribute("comment", commentUpdateDto);
        return "commentAddForm";
    }


}
