package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA + Hibernate для работы с книгами")
@DataJpaTest
@Import({JpaBookRepository.class})
class JpaBookRepositoryTest {

    @Autowired
    private JpaBookRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;


    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        var expectedBooks = getDbBooks();
        for (Book expectedBook : expectedBooks) {
            var actualBook = repositoryJpa.findById(expectedBook.getId());

            assertThat(actualBook).isPresent()
                    .get()
                    .usingRecursiveComparison()
                    .comparingOnlyFields("id")
                    .isEqualTo(expectedBook);
        }
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = repositoryJpa.findAll();
        var expectedBooks = getDbBooks();

        assertThat(actualBooks).containsExactlyElementsOf(expectedBooks);
        actualBooks.forEach(System.out::println);
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var author = em.find(Author.class, 1L);
        var genre = em.find(Genre.class, 1L);
        var bookToAdd = new Book(0, "BookTitle_10500", author, genre, null);
        var comment = new Comment(0, bookToAdd, "Не надо это читать");
        bookToAdd.setComments(List.of(comment));
        repositoryJpa.save(bookToAdd);

        em.flush();
        em.clear();

        var returnedBook = em.find(Book.class, bookToAdd.getId());
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(bookToAdd);
    }


    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {

        var bookToUpdate = em.find(Book.class, 1L);
        bookToUpdate.setTitle("Измененная книга");

        repositoryJpa.save(bookToUpdate);

        em.flush();
        em.clear();

        var returnedBook = em.find(Book.class, bookToUpdate.getId());
        assertThat(returnedBook.getTitle()).isEqualTo("Измененная книга");
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        var bookToDelete = em.find(Book.class, 1L);
        var bookIdToDelete = bookToDelete.getId();
        em.clear();

        repositoryJpa.deleteById(bookIdToDelete);

        var shouldBeNull = em.find(Book.class, bookIdToDelete);
        assertThat(shouldBeNull).isEqualTo(null);
    }

    private List<Book> getDbBooks() {
        List<Book> books = IntStream.range(1, 4).boxed()
                .map(id -> em.find(Book.class, id))
                .toList();

        em.clear();
        return  books;
    }
}