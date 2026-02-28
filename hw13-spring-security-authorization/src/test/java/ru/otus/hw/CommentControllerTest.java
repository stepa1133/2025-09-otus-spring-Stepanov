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
import ru.otus.hw.controller.CommentController;
import ru.otus.hw.converters.dto.CommentDtoConverter;
import ru.otus.hw.converters.dto.CommentUpdateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.CommentServiceImpl;
import ru.otus.hw.services.acl.AclServiceWrapperService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
@Import(SecurityConfiguration.class)
public class CommentControllerTest {

    @MockitoBean
    private CommentServiceImpl service;

    @MockitoBean
    private CommentDtoConverter commentDtoConverter;
    @MockitoBean
    private AclServiceWrapperService aclServiceWrapperService;


    @Autowired
    private MockMvc mvc;

    private List<CommentDto> comments = List.of(new CommentDto(1L, new Book(),"Good"),
            new CommentDto(2L, new Book(),"Bad"));


    @ParameterizedTest
    @ValueSource(strings = {"getCommentsList", "getAddCommentaryForm"})
    void unauthorizedUserShouldGet302(String uri) throws Exception {
        mvc.perform(get("/{uri}", uri))
                .andExpect(status().isMovedTemporarily());
    }




    @ParameterizedTest
    @CsvSource({
            "editor, ROLE_EDITOR, 200",
            "reader, ROLE_READER, 200",
            "free, ROLE_FREEREADER, 403"
    })
    void shouldRenderListPageCorrectViewAndModelAttributes(String username, String role,
                                                                    int expectedStatus) throws Exception {
        when(service.findAllBookComments(1)).thenReturn(comments);
        mvc.perform(get("/getCommentsList").with(user(username)
                        .authorities(new SimpleGrantedAuthority(role))).param("id", "1"))
                .andExpect(status().is(expectedStatus));
    }

    @ParameterizedTest
    @CsvSource({
            "editor, ROLE_EDITOR, 200",
            "reader, ROLE_READER, 200",
            "free, ROLE_FREEREADER, 403"
    })
    void shouldRenderAddCommentaryForm(String username, String role,
                                       int expectedStatus) throws Exception {
        var commentUpdateDto = new CommentUpdateDto();
        commentUpdateDto.setBookId(1L);
        mvc.perform(get("/getAddCommentaryForm")
                        .with(user(username).authorities(new SimpleGrantedAuthority(role)))
                        .param("id", "1"))
                .andExpect(status().is(expectedStatus));
    }


    @ParameterizedTest
    @CsvSource({
            "editor, ROLE_EDITOR, 302",
            "reader, ROLE_READER, 302",
            "free, ROLE_FREEREADER, 403"
    })
    void shouldUnauthorizedSaveNewCommentToCurrentBook(String username, String role,
                                                       int expectedStatus) throws Exception {
        when(service.insert(1L, "Good")).thenReturn(comments.get(0));
        mvc.perform(post("/insertComment")
                .with(user(username).authorities(new SimpleGrantedAuthority(role)))
                        .param("id", "1")
                        .param("bookId", "1")
                        .param("commentary", "Good"))
                .andExpect(status().is(expectedStatus));
    }

    @ParameterizedTest
    @CsvSource({
            "editor, ROLE_EDITOR, 302",
            "reader, ROLE_READER, 302",
            "free, ROLE_FREEREADER, 403"
    })
    void shouldRenderSaveNewCommentToCurrentBook(String username, String role,
                                                 int expectedStatus) throws Exception {
        when(service.insert(1L, "Good")).thenReturn(comments.get(0));
        mvc.perform(post("/insertComment")
                        .with(user(username).authorities(new SimpleGrantedAuthority(role)))
                        .with(csrf()).param("id", "1")
                                                    .param("bookId", "1")
                                                    .param("commentary", "Good"))
                .andExpect(status().is(expectedStatus));
    }


    @ParameterizedTest
    @CsvSource({
            "editor, ROLE_EDITOR, 302",
            "reader, ROLE_READER, 403",
            "free, ROLE_FREEREADER, 403"
    })
    void shouldUnauthorizedRenderDeleteCommentToCurrentBook(String username, String role,
                                                            int expectedStatus) throws Exception {
        doNothing().when(service).deleteById(1);
        mvc.perform(post("/commentary/delete/1/1")
                .with(user(username).authorities(new SimpleGrantedAuthority(role))))
                .andExpect(status().is(expectedStatus));

    }

    @ParameterizedTest
    @CsvSource({
            "editor, ROLE_EDITOR, 302",
            "reader, ROLE_READER, 403",
            "free, ROLE_FREEREADER, 403"
    })
    void shouldRenderDeleteCommentToCurrentBook(String username, String role,
                                                int expectedStatus) throws Exception {
        doNothing().when(service).deleteById(1);
        mvc.perform(post("/commentary/delete/1/1")
                        .with(csrf())
                .with(user(username).authorities(new SimpleGrantedAuthority(role))))
                .andExpect(status().is(expectedStatus));
    }
}
