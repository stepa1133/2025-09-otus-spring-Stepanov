package ru.otus.hw.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ViewController {

    @GetMapping("/book")
    public String getListBooksPage(Model model) {
        return "list";
    }

    @GetMapping("/book/{id}")
    public String getEditCurBookPage() {
        return "bookEditForm";
    }

    @GetMapping("/book/new")
    public String getNewBookPage() {
        return "bookAddForm";
    }

    @GetMapping("/book/{id}/comment/new")
    public String addCommentaryPage() {
        return "commentAddForm";
    }

    @GetMapping("/book/{id}/comment")
    public String getCommentsBookListPage() {
        return "commentsList";
    }

    @GetMapping("/author")
    public String getAuthorsListPage() {
        return "authorsList";
    }

    @GetMapping("/genre")
    public String genresListPage() {
        return "genresList";
    }
}
