package ru.otus.hw.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "comments")
public class Comment {
    @Id
    private Long id;

//    @JoinColumn(name = "book_id")
    private Book book;

    private String commentary;


}
