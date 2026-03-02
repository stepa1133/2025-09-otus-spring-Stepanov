package ru.otus.hw.models.mongo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Getter

@Document(collection = "authors")
public class MongoAuthor {
    @Id
    private String id;

    @Field("full_name")
    private String fullName;
}
