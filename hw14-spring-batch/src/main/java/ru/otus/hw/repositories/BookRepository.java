package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @Nonnull
    @EntityGraph(value = "book-with-author-genre-entity-graph")
    Page<Book> findAll(Pageable pageable);
}