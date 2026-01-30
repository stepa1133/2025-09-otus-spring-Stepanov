package ru.otus.hw.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.converters.dto.CommentUpdateDto;

@Controller
public class ViewController {
    @GetMapping("/book") //+
    public String getListBooksPage(Model model) {
        return "list";
    }

    @GetMapping("/book/{id}")
    public String getEditCurBookPage(@PathVariable long id) {
//        Mono<BookDto> book = bookService.findById(id);
//        Flux<AuthorDto> allAuthors = authorService.findAll();
//        Flux<GenreDto> allGenres = genreService.findAll();
//        *//*BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
//                                                                    book.getAuthor().getId(), book.getGenre().getId());*//*
//        model.addAttribute("book", book);
//        model.addAttribute("allAuthors", allAuthors);
//        model.addAttribute("allGenres", allGenres);
        return "bookEditForm";
    }

    @GetMapping("/book/{id}/comment/new") //+
    public String addCommentaryPage(@PathVariable("id") long bookId, Model model) {
        return "commentAddForm";
    }

    @GetMapping("/book/{id}/comment") //+
    public String getCommentsBookListPage(@PathVariable("id") long bookId, Model model) {
/*        Flux<CommentDto> comments = commentService.findAllBookComments(bookId);
        model.addAttribute("comments", comments);
        model.addAttribute("bookId", bookId);*/
        return "commentsList";
    }

    @GetMapping("/author")
    public String getAuthorsListPage(Model model) {
/*        Flux<AuthorDto> authors = authorService.findAll();
        model.addAttribute("authors", authors);*/
        return "authorsList";
    }

    @GetMapping("/genre")
    public String genresListPage(Model model) {
/*        List<GenreDto> genres = genreService.findAll();
        model.addAttribute("genres", genres);*/
        return "genresList";
    }
}
