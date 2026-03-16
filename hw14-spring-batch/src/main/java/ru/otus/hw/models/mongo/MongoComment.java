package ru.otus.hw.models.mongo;

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
public class MongoComment {
    @Id
    private String id;

    @DBRef(lazy = true)
    private MongoBook book;

    private String commentary;
}
