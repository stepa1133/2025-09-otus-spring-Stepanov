package ru.otus.hw;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.GenreController;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
@Import(SecurityConfiguration.class)
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
                .andExpect(status().isMovedTemporarily());
    }

    @Test
    @WithMockUser
    void shouldRenderListPageWithCorrectViewAndModelAttributes() throws Exception {
        when(service.findAll()).thenReturn(genres);
        mvc.perform(get("/getGenresList") .with(user("editor")
                        .authorities(new SimpleGrantedAuthority("ROLE_EDITOR"))))
                .andExpect(view().name("genresList"))
                .andExpect(model().attribute("genres", genres));
    }

    @ParameterizedTest
    @CsvSource({
            "editor, ROLE_EDITOR, 200",
            "reader, ROLE_READER, 200",
            "free, ROLE_FREEREADER, 403"
    })
    void testAccessByUser(String username, String role, int expectedStatus) throws Exception {
        mvc.perform(get("/getAuthorsList")
                        .with(user(username).authorities(new SimpleGrantedAuthority(role))))
                .andExpect(status().is(expectedStatus));
    }

}
