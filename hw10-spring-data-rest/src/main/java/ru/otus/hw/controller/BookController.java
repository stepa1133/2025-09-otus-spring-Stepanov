package ru.otus.hw.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.converters.dto.BookUpdateDto;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;

    private final AuthorServiceImpl authorService;

    private final GenreServiceImpl genreService;

    @GetMapping("/book")
    public String getListBooksPage(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/book/{id}")
    public String getEditCurBookPage(@PathVariable long id, Model model) {
        BookDto book = bookService.findById(id).get();
        List<AuthorDto> allAuthors = authorService.findAll();
        List<GenreDto> allGenres = genreService.findAll();
        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                                                                    book.getAuthor().getId(), book.getGenre().getId());
        model.addAttribute("book", bookUpdateDto);
        model.addAttribute("allAuthors", allAuthors);
        model.addAttribute("allGenres", allGenres);
        return "bookEditForm";
    }



    @GetMapping("/book/new")
    public String getNewBookPage(Model model) {
        List<AuthorDto> allAuthors = authorService.findAll();
        List<GenreDto> allGenres = genreService.findAll();
        model.addAttribute("book", new BookUpdateDto());
        model.addAttribute("allAuthors", allAuthors);
        model.addAttribute("allGenres", allGenres);
        return "bookAddForm";
    }





}
