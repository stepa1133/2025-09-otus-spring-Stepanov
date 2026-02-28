package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.converters.dto.CommentDtoConverter;
import ru.otus.hw.converters.dto.CommentUpdateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentServiceImpl;
import ru.otus.hw.services.acl.AclServiceWrapperService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    private final AclServiceWrapperService aclServiceWrapperService;

    private final CommentDtoConverter commentDtoConverter;

    @GetMapping("/getCommentsList")
    public String commentsListPage(@RequestParam("id") long bookId, Model model) {
        List<CommentDto> comments = commentService.findAllBookComments(bookId);
        model.addAttribute("comments", comments);
        model.addAttribute("bookId", bookId);
        return "commentsList";
    }

    @GetMapping("/getAddCommentaryForm")
    public String addCommentaryPage(@RequestParam("id") long bookId, Model model) {
        var commentUpdateDto = new CommentUpdateDto();
        commentUpdateDto.setBookId(bookId);
        model.addAttribute("comment", commentUpdateDto);
        return "commentAddForm";
    }

    @PostMapping("/insertComment")
    public String insertComment(@Valid @ModelAttribute("comment") CommentUpdateDto commentUpdateDto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "commentAddForm";
        }
        CommentDto commentDto = commentService.insert(commentUpdateDto.getBookId(), commentUpdateDto.getCommentary());
        aclServiceWrapperService.createPermission(commentDtoConverter.toDomain(commentDto), BasePermission.READ);
        return "redirect:/getCommentsList?id=" + commentUpdateDto.getBookId();
    }

    @PostMapping("/commentary/delete/{id}/{bookId}")
    public String deleteComment(@PathVariable("id") long id, @PathVariable("bookId") long bookId, Model model) {
        commentService.deleteById(id);
        List<CommentDto> comments = commentService.findAllBookComments(bookId);
        model.addAttribute("comments", comments);
        model.addAttribute("bookId", bookId);
        return "redirect:/getCommentsList?id=%s".formatted(bookId);
    }
}
