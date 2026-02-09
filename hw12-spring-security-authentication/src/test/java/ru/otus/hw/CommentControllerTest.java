package ru.otus.hw;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.CommentController;
import ru.otus.hw.converters.dto.CommentUpdateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.CommentServiceImpl;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @MockitoBean
    private CommentServiceImpl service;

    @Autowired
    private MockMvc mvc;

    private List<CommentDto> comments = List.of(new CommentDto(1L, new Book(),"Good"),
            new CommentDto(2L, new Book(),"Bad"));


    @ParameterizedTest
    @ValueSource(strings = {"getCommentsList", "getAddCommentaryForm"})
    void unauthorizedUserShouldGet401(String uri) throws Exception {
        mvc.perform(get("/{uri}", uri))
                .andExpect(status().isUnauthorized());
    }




    @Test
    @WithMockUser
    void shouldRenderListPageWithCorrectViewAndModelAttributes() throws Exception {
        when(service.findAllBookComments(1)).thenReturn(comments);
        mvc.perform(get("/getCommentsList").param("id", "1"))
                .andExpect(view().name("commentsList"))
                .andExpect(model().attribute("comments", comments))
                .andExpect(model().attribute("bookId", 1L));
    }

    @Test
    @WithMockUser
    void shouldRenderAddCommentaryForm() throws Exception {
        var commentUpdateDto = new CommentUpdateDto();
        commentUpdateDto.setBookId(1L);
        mvc.perform(get("/getAddCommentaryForm").param("id", "1"))
                .andExpect(view().name("commentAddForm"))
                .andExpect(model().attribute("comment", commentUpdateDto));
    }


    @Test
    void shouldUnauthorizedSaveNewCommentToCurrentBook() throws Exception {
        when(service.insert(1L, "Good")).thenReturn(comments.get(0));
        mvc.perform(post("/insertComment")
                        .param("id", "1")
                        .param("bookId", "1")
                        .param("commentary", "Good"))

                .andExpect(status().is4xxClientError());
    }
    @Test
    @WithMockUser
    void shouldRenderSaveNewCommentToCurrentBook() throws Exception {
        when(service.insert(1L, "Good")).thenReturn(comments.get(0));
        mvc.perform(post("/insertComment").with(csrf()).param("id", "1")
                                                    .param("bookId", "1")
                                                    .param("commentary", "Good"))
                .andExpect((view().name("redirect:/getCommentsList?id=1")));
    }


    @Test
    void shouldUnauthorizedRenderDeleteCommentToCurrentBook() throws Exception {
        doNothing().when(service).deleteById(1);
        mvc.perform(post("/commentary/delete/1/1")).andExpect(status().is4xxClientError());

    }

    @Test
    @WithMockUser
    void shouldRenderDeleteCommentToCurrentBook() throws Exception {
        doNothing().when(service).deleteById(1);
        mvc.perform(post("/commentary/delete/1/1").with(csrf()))
                .andExpect((view().name("redirect:/getCommentsList?id=1")));
    }
}
