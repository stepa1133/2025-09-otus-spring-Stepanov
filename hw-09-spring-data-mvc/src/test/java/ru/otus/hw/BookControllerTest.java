package ru.otus.hw;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.BookController;
import ru.otus.hw.converters.dto.BookUpdateDto;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@WebMvcTest(BookController.class)
public class BookControllerTest {
    @MockitoBean
    private BookServiceImpl bookService;

    @MockitoBean
    private  AuthorServiceImpl authorService;

    @MockitoBean
    private  GenreServiceImpl genreService;

    @Autowired
    private MockMvc mvc;


    @Test
    void shouldRenderListPageWithCorrectViewAndModelAttributes() throws Exception {
        List<BookDto> books = getDbBooks();
        when(bookService.findAll()).thenReturn(books);
        mvc.perform(get("/"))
                .andExpect(view().name("list"))
                .andExpect(model().attribute("books", books));
    }

    @Test
    void shouldRenderEditBookForm() throws Exception {
        var expectedBook = Optional.ofNullable(getDbBooks().get(0));
        var expectedGenres = getDbGenres();
        var expectedAuthors = getDbAuthors();
        when(bookService.findById(1)).thenReturn(expectedBook);
        when(genreService.findAll()).thenReturn(expectedGenres);
        when(authorService.findAll()).thenReturn(expectedAuthors);



        mvc.perform(get("/getBookEditForm").param("id", "1"))
                .andExpect(view().name("bookEditForm"))
                .andExpect(model().attribute("book", new BookUpdateDto(
                        expectedBook.get().getId(),
                        expectedBook.get().getTitle(),
                        expectedBook.get().getAuthor().getId(),
                        expectedBook.get().getGenre().getId())))
                .andExpect(model().attribute("allAuthors", expectedAuthors))
                .andExpect(model().attribute("allGenres", expectedGenres));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        var expectedBook = Optional.ofNullable(getDbBooks().get(0));
        when(bookService.insert(
                expectedBook.get().getTitle(),
                expectedBook.get().getAuthor().getId(),
                expectedBook.get().getGenre().getId())).thenReturn(expectedBook.get());
        mvc.perform(post("/updateBook")
                        .param("id", "1")
                        .param("title", expectedBook.get().getTitle())
                        .param("authorId", String.valueOf(expectedBook.get().getAuthor().getId()))
                        .param("genreId", String.valueOf(expectedBook.get().getGenre().getId()))
                       )
                .andExpect((view().name("redirect:/")));
    }

    @Test
    void shouldInsertNewBook() throws Exception {
        var expectedBook = Optional.ofNullable(getDbBooks().get(0));
        when(bookService.insert(
                expectedBook.get().getTitle(),
                expectedBook.get().getAuthor().getId(),
                expectedBook.get().getGenre().getId())).thenReturn(expectedBook.get());
        mvc.perform(post("/insertBook")
                        .param("id", "1")
                        .param("title", expectedBook.get().getTitle())
                        .param("authorId", String.valueOf(expectedBook.get().getAuthor().getId()))
                        .param("genreId", String.valueOf(expectedBook.get().getGenre().getId()))
                )
                .andExpect((view().name("redirect:/")));
    }

    @Test
    void shouldRenderDeleteCurrentBook() throws Exception {
        doNothing().when(bookService).deleteById(1);
        mvc.perform(post("/delete/1"))
                .andExpect((view().name("redirect:/")));
    }





    private List<BookDto> getDbBooks() {
        var book1 = new BookDto(1, "Gold egg", getDbAuthors().get(0), getDbGenres().get(0), null);
        var book2 = new BookDto(2, "Shotgun", getDbAuthors().get(1), getDbGenres().get(1), null);
        return List.of(book1, book2);
    }

    private List<AuthorDto> getDbAuthors() {
        var author1 = new AuthorDto(1, "Elena Vorobey");
        var author2 = new AuthorDto(2, "Kurt Cobain");
        return List.of(author1, author2);
    }

    private List<GenreDto> getDbGenres() {
        var genre1 = new GenreDto(1, "Comedy");
        var genre2 = new GenreDto(2, "Scary");
        return List.of(genre1, genre2);
    }
}
