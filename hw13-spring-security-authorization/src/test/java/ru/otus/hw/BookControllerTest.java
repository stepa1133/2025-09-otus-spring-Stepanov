package ru.otus.hw;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.BookController;
import ru.otus.hw.converters.dto.BookDtoConverter;
import ru.otus.hw.converters.dto.BookUpdateDto;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;
import ru.otus.hw.services.acl.AclServiceWrapperService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
public class BookControllerTest {
    @MockitoBean
    private BookServiceImpl bookService;

    @MockitoBean
    private  AuthorServiceImpl authorService;

    @MockitoBean
    private AclServiceWrapperService aclServiceWrapperService;

    @MockitoBean
    private BookDtoConverter bookDtoConverter;


    @MockitoBean
    private  GenreServiceImpl genreService;

    @Autowired
    private MockMvc mvc;


    @ParameterizedTest
    @ValueSource(strings = {"getAuthorsList", "", "getBookEditForm"})
    void unauthorizedUserShouldGet302(String uri) throws Exception {
        mvc.perform(get("/{uri}", uri))
                .andExpect(status().isMovedTemporarily());
    }

    @ParameterizedTest
    @CsvSource({
            "editor, ROLE_EDITOR, 200",
            "reader, ROLE_READER, 200",
            "free, ROLE_FREEREADER, 200"
    })
    void shouldRenderListPageWithCorrectViewAndModelAttributes(String username, String role) throws Exception {
        List<BookDto> books = getDbBooks();
        mvc.perform(get("/").with(user(username).authorities(new SimpleGrantedAuthority(role))))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource({
            "editor, ROLE_EDITOR, 200",
            "reader, ROLE_READER, 403",
            "free, ROLE_FREEREADER, 403"
    })
    void shouldRenderEditBookForm(String username, String role,
                                  int expectedStatus) throws Exception {
        var expectedBook = Optional.ofNullable(getDbBooks().get(0));
        var expectedGenres = getDbGenres();
        var expectedAuthors = getDbAuthors();
        when(bookService.findById(1)).thenReturn(expectedBook);
        when(genreService.findAll()).thenReturn(expectedGenres);
        when(authorService.findAll()).thenReturn(expectedAuthors);
        mvc.perform(get("/getBookEditForm").param("id", "1")
                        .with(user(username).authorities(new SimpleGrantedAuthority(role))))
                .andExpect(status().is(expectedStatus));
    }

    @ParameterizedTest
    @CsvSource({
            "editor, ROLE_EDITOR, 302",
            "reader, ROLE_READER, 403",
            "free, ROLE_FREEREADER, 403"
    })
    void shouldUpdateBook(String username, String role,
                          int expectedStatus) throws Exception {
        var expectedBook = getDbBooks().get(0);

        mvc.perform(post("/updateBook")
                        .with(csrf())
                        .with(user(username).authorities(new SimpleGrantedAuthority(role)))
                        .flashAttr("book", new BookUpdateDto(
                                expectedBook.getId(),
                                expectedBook.getTitle(),
                                expectedBook.getAuthor().getId(),
                                expectedBook.getGenre().getId()
                        ))
                )
                .andExpect(status().is(expectedStatus));
    }

    @ParameterizedTest
    @CsvSource({
            "editor, ROLE_EDITOR, 302",
            "reader, ROLE_READER, 403",
            "free, ROLE_FREEREADER, 403"
    })
    void shouldInsertNewBook(String username, String role,
                             int expectedStatus) throws Exception {
        var expectedBook = Optional.ofNullable(getDbBooks().get(0));
        when(bookService.insert(
                expectedBook.get().getTitle(),
                expectedBook.get().getAuthor().getId(),
                expectedBook.get().getGenre().getId())).thenReturn(expectedBook.get());
        mvc.perform(post("/insertBook")

                        .with(csrf())
                        .with(user(username).authorities(new SimpleGrantedAuthority(role)))
                        .param("id", "1")
                        .param("title", expectedBook.get().getTitle())
                        .param("authorId", String.valueOf(expectedBook.get().getAuthor().getId()))
                        .param("genreId", String.valueOf(expectedBook.get().getGenre().getId()))
                )
                .andExpect(status().is(expectedStatus));
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
