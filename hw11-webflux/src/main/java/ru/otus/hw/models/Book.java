package ru.otus.hw.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/*@Entity
@Table(name = "books")
@NamedEntityGraph(
        name = "book-with-author-comments-genre-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("author"),
                @NamedAttributeNode("comments"),
                @NamedAttributeNode("genre")
        }
)
@NamedEntityGraph(
        name = "book-with-author-genre-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("author"),
                @NamedAttributeNode("genre")
        }
)*/
@Table(name = "books")
public class Book {
    @Id
    private Long id;

    private String title;

    private Author author;

    private Genre genre;

    @Setter
    private List<Comment> comments;
}
