package ru.otus.hw.config.books;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.repositories.BookRepository;

import java.util.Collections;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BooksStepConfig {

    private static final int CHUNK_SIZE = 5;

    private final Map<String, Sort.Direction> sortsMap = Collections.singletonMap("id", Sort.Direction.ASC);

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final BookRepository bookRepository;

    private final MongoTemplate mongoTemplate;


    @Bean
    public Step transformBooksStep(RepositoryItemReader<Book> reader, BookProcessor processor,
                                                                                 MongoItemWriter<MongoBook> writer) {
        return new StepBuilder("transformBooksStep", jobRepository)
                .<Book, MongoBook> chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

    }

    @Bean
    public RepositoryItemReader<Book> bookItemReader() {
        RepositoryItemReader<Book> reader = new RepositoryItemReader<>();
        reader.setName("bookReader");
        reader.setSort(sortsMap);
        reader.setRepository(bookRepository);
        reader.setMethodName("findAll");
        reader.setPageSize(CHUNK_SIZE);
        return reader;
    }


    @Bean
    public MongoItemWriter<MongoBook> bookWriter() {
        MongoItemWriter<MongoBook> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("books");
        return writer;
    }


}
