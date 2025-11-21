package ru.otus.hw.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с авторами")
@DataJpaTest
public class JpaAuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorList() {
        var actualAuthors = repository.findAll();
        var expectedAuthors = getDbAuthors();

        assertThat(actualAuthors).containsExactlyElementsOf(expectedAuthors);
    }

    @DisplayName("должен загружать автора по итендификатору")
    @Test
    void shouldReturnCorrectAuthor() {
        var expectedAuthors = getDbAuthors();
        for (var expectedAuthor: expectedAuthors) {
            var actualAuthor = repository.findById(expectedAuthor.getId());
            Assertions.assertThat(actualAuthor).isPresent()
                    .get()
                    .usingRecursiveComparison()
                    .isEqualTo(expectedAuthor);
        }
    }

    private List<Author> getDbAuthors() {
        List<Author> authors = IntStream.range(1, 4).boxed()
                .map(id -> em.find(Author.class, id))
                .toList();

        em.clear();
        return authors;
    }
}
