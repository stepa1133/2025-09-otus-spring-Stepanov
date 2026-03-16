package ru.otus.hw.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "books")
public class MongoBook {
    @Id
    private String id;

    private String title;

    @DBRef
    private MongoAuthor author;

    @DBRef
    private MongoGenre genre;

}
