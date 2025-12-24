package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document
public class Comment {
    @Id
    private String id;

    @DBRef(lazy = true)
    private Book book;

    private String commentary;

}
