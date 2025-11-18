package ru.otus.hw.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями")
@DataJpaTest
@Import({JpaCommentRepository.class})
public class JpaCommentRepositoryTest {

    @Autowired
    private JpaCommentRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать коментарий по итендификатору")
    @Test
    void shouldReturnCorrectAuthorList() {
        var expectedComments = getDbComments();
        for (var expectedComment: expectedComments) {
            var actualComment = repositoryJpa.findById(expectedComment.getId());
            Assertions.assertThat(actualComment).isPresent()
                    .get()
                    .usingRecursiveComparison()
                    .comparingOnlyFields("id")
                    .isEqualTo(expectedComment);

        }
    }

    private List<Comment> getDbComments(){
        List<Comment> comments = IntStream.range(1, 4).boxed()
                .map(id -> em.find(Comment.class, id))
                .toList();

        em.clear();
        return comments;
    }
}
