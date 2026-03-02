package ru.otus.hw.config.authors;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.models.Author;
import org.springframework.data.domain.Sort;
import ru.otus.hw.models.mongo.MongoAuthor;
import ru.otus.hw.repositories.AuthorRepository;
import java.util.Collections;
import java.util.Map;

@Configuration
public class AuthorsStepConfig {
    private static final int CHUNK_SIZE = 5;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Map<String, Sort.Direction> sortsMap = Collections.singletonMap("id", Sort.Direction.ASC);

    @Bean
    public Step transformAuthorsStep(RepositoryItemReader<Author> reader,
                                                AuthorProcessor processor, MongoItemWriter<MongoAuthor> writer) {
        return new StepBuilder("transformAuthorStep", jobRepository)
                .<Author, MongoAuthor>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public RepositoryItemReader<Author> authorItemReader() {
        RepositoryItemReader<Author> reader = new RepositoryItemReader<>();
        reader.setName("authorReader");
        reader.setSort(sortsMap);
        reader.setRepository(authorRepository);
        reader.setMethodName("findAll");
        reader.setPageSize(CHUNK_SIZE);
        return reader;
    }

    @Bean
    public MongoItemWriter<MongoAuthor> authorWriter() {
        MongoItemWriter<MongoAuthor> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("authors");
        return writer;
    }
}
