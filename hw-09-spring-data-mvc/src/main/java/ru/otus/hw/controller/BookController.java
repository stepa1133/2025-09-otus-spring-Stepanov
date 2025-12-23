package ru.otus.hw.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/getBookEditForm")
    public String editBookPage(@RequestParam long id, Model model) {
        BookDto book = bookService.findById(id).get();
        List<AuthorDto> allAuthors = authorService.findAll();
        List<GenreDto> allGenres = genreService.findAll();
        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getGenre().getId());
        model.addAttribute("book", bookUpdateDto);
        model.addAttribute("allAuthors", allAuthors);
        model.addAttribute("allGenres", allGenres);
        return "bookEditForm";
    }

    @PostMapping("/updateBook")
    public String updateBook(@Valid @ModelAttribute("book") BookUpdateDto book,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "bookEditForm";
        }
        bookService.update(book.getId(), book.getTitle(), book.getAuthorId(), book.getGenreId());
        return "redirect:/";
    }

    @GetMapping("/getBookAddForm")
    public String addBook(Model model) {
        List<AuthorDto> allAuthors = authorService.findAll();
        List<GenreDto> allGenres = genreService.findAll();
        model.addAttribute("book", new BookUpdateDto());
        model.addAttribute("allAuthors", allAuthors);
        model.addAttribute("allGenres", allGenres);
        return "bookAddForm";
    }

    @PostMapping("/insertBook")
    public String insertBook(@Valid @ModelAttribute("book") BookUpdateDto bookUpdateDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            List<AuthorDto> allAuthors = authorService.findAll();
            List<GenreDto> allGenres = genreService.findAll();
            model.addAttribute("allAuthors", allAuthors);
            model.addAttribute("allGenres", allGenres);
            return "bookAddForm";
        }
        bookService.insert(bookUpdateDto.getTitle(), bookUpdateDto.getAuthorId(), bookUpdateDto.getGenreId());
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable("id") long id, Model model) {
        bookService.deleteById(id);
        return "redirect:/";
    }

}
