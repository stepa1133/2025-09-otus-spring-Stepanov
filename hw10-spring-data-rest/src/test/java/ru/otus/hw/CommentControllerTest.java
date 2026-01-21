package ru.otus.hw;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.hw.controller.CommentController;
import ru.otus.hw.converters.dto.CommentUpdateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.rest.CommentRestController;
import ru.otus.hw.services.CommentServiceImpl;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({CommentController.class, CommentRestController.class})
public class CommentControllerTest {

    @MockitoBean
    private CommentServiceImpl service;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<CommentDto> comments = List.of(new CommentDto(1L, new Book(),"Good"),
            new CommentDto(2L, new Book(),"Bad"));

    @Test
    void shouldRenderListPageWithCorrectViewAndModelAttributes() throws Exception {
        Mockito.when(service.findAllBookComments(1)).thenReturn(comments);
        mvc.perform(get("/book/1/comment"))
                .andExpect(MockMvcResultMatchers.view().name("commentsList"))
                .andExpect(MockMvcResultMatchers.model().attribute("comments", comments))
                .andExpect(MockMvcResultMatchers.model().attribute("bookId", 1L));
    }

    @Test
    void shouldRenderAddCommentaryForm() throws Exception {
        var commentUpdateDto = new CommentUpdateDto();
        commentUpdateDto.setBookId(1L);
        mvc.perform(get("/book/1/comment/new").param("id", "1"))
                .andExpect(MockMvcResultMatchers.view().name("commentAddForm"))
                .andExpect(MockMvcResultMatchers.model().attribute("comment", commentUpdateDto));
    }

    @Test
    void shouldRenderSaveNewCommentToCurrentBook() throws Exception {
        CommentDto commentDto = comments.get(1);
        CommentUpdateDto commentUpdateDto =
                new CommentUpdateDto(commentDto.getId(), commentDto.getBook().getId(), commentDto.getCommentary());
        Mockito.when(service.insert(0L, "Good")).thenReturn(commentDto);
        mvc.perform(post("/book/0/comment/").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentUpdateDto)))
                        .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1))
                .insert(commentUpdateDto.getBookId(), commentUpdateDto.getCommentary());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6})
    void shouldRenderDeleteCommentToCurrentBook(Long commentId) throws Exception {
        mvc.perform(delete("/book/1/comment/{commentId}", commentId)).andExpect(status().isNoContent());
        verify(service).deleteById(commentId);
    }

}
