package ru.otus.hw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.AuthorController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.AuthorServiceImpl;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @MockitoBean
    private AuthorServiceImpl service;

    @Autowired
    private MockMvc mvc;

    private List<AuthorDto> authors = List.of(new AuthorDto(1L, "Kristina Orbakayte"),
            new AuthorDto(1L, "Iosif Kobzon"));


    @Test
    void shouldRenderListPageWithCorrectViewAndModelAttributes() throws Exception {
        when(service.findAll()).thenReturn(authors);
        mvc.perform(get("/getAuthorsList"))
                .andExpect(view().name("authorsList"))
                .andExpect(model().attribute("authors", authors));
    }

}
