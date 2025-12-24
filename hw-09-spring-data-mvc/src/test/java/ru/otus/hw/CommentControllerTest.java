package ru.otus.hw;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.CommentController;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.CommentServiceImpl;

import java.util.List;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @MockitoBean
    private CommentServiceImpl service;

    @Autowired
    private MockMvc mvc;

/*    private List<CommentDto> comments = List.of(new CommentDto(1L, "Good"),
            new CommentDto(1L, "Scary"));*/
}
