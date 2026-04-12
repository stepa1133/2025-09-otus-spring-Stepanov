package ru.otus.hw;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.BookController;
import ru.otus.hw.converters.dto.BookUpdateDto;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.rest.BookRestController;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest({BookRestController.class, BookController.class})
public class BookControllerTest {
    @MockitoBean
    private BookServiceImpl bookService;

    @MockitoBean
    private  AuthorServiceImpl authorService;

    @MockitoBean
    private  GenreServiceImpl genreService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;


    @Test
    void shouldRenderListPageWithCorrectViewAndModelAttributes() throws Exception {
        List<BookDto> books = getDbBooks();
        when(bookService.findAll()).thenReturn(books);
        mvc.perform(get("/book"))
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



        mvc.perform(get("/book/1"))
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
        BookDto bookDto = getDbBooks().get(0);
        BookUpdateDto bookToSend = new BookUpdateDto(
                bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getAuthor().getId(),
                bookDto.getGenre().getId());

        when(bookService.update(bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthor().getId(), bookDto.getGenre().getId()))
                .thenReturn(bookDto);

        mvc.perform(put("/book/{id}", bookDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookToSend))).andExpect(status().isOk());
        verify(bookService, times(1)).update(  bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getAuthor().getId(),
                bookDto.getGenre().getId());
    }


    @Test
    void shouldInsertNewBook() throws Exception {
        BookDto newBook = getDbBooks().get(0);
        BookUpdateDto newBookUpdateDto = new BookUpdateDto(1L, newBook.getTitle(),
                newBook.getAuthor().getId(),
                newBook.getGenre().getId());
        when(bookService.insert(
                newBook.getTitle(),
                newBook.getAuthor().getId(),
                newBook.getGenre().getId())).thenReturn(newBook);
        mvc.perform(post("/book").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBookUpdateDto)))
                        .andExpect(status().isOk());
        verify(bookService, times(1)).insert(
                newBook.getTitle(),
                newBook.getAuthor().getId(),
                newBook.getGenre().getId()
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6})
    void shouldRenderDeleteCurrentBook(Long bookId) throws Exception {
        mvc.perform(delete("/book/{bookId}", bookId)).andExpect(status().isNoContent());
        verify(bookService).deleteById(bookId);
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
