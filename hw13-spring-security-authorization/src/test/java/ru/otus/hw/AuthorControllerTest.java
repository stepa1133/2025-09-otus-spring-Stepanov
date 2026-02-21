package ru.otus.hw;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.AuthorController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorServiceImpl;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthorController.class)
@Import(SecurityConfiguration.class)
public class AuthorControllerTest {

    @MockitoBean
    private AuthorServiceImpl service;

    @Autowired
    private MockMvc mvc;

    private List<AuthorDto> authors = List.of(new AuthorDto(1L, "Kristina Orbakayte"),
            new AuthorDto(1L, "Iosif Kobzon"));


    @Test
    void unauthorizedUserShouldGet302() throws Exception {
        mvc.perform(get("/getAuthorsList"))
                .andExpect(status().isMovedTemporarily());
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



    @Test
    @WithMockUser
    void shouldRenderListPageWithCorrectViewAndModelAttributes() throws Exception {
        when(service.findAll()).thenReturn(authors);
        mvc.perform(get("/getAuthorsList")
                        .with(user("editor")
                                .authorities(new SimpleGrantedAuthority("ROLE_EDITOR"))))
                .andExpect(view().name("authorsList"))
                .andExpect(model().attribute("authors", authors))
                .andExpect(status().isOk());
    }

}
