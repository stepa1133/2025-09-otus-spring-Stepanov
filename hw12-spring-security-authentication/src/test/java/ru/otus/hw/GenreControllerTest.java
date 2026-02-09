package ru.otus.hw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.GenreController;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @MockitoBean
    private GenreServiceImpl service;

    @Autowired
    private MockMvc mvc;

    private List<GenreDto> genres = List.of(new GenreDto(1L, "Adventure"),
            new GenreDto(1L, "Scary"));



    @Test
    void shouldUnauthorizedRenderListPageWithCorrectViewAndModelAttributes() throws Exception {
        when(service.findAll()).thenReturn(genres);
        mvc.perform(get("/getGenresList"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldRenderListPageWithCorrectViewAndModelAttributes() throws Exception {
        when(service.findAll()).thenReturn(genres);
        mvc.perform(get("/getGenresList"))
                .andExpect(view().name("genresList"))
                .andExpect(model().attribute("genres", genres));
    }
}
