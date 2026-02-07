package ru.otus.hw.models;

;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "authors")
public class Author {
    @Id
    private Long id;

    private String fullName;
}
