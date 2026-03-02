package ru.otus.hw.config.genres;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.mongo.MongoGenre;
import ru.otus.hw.repositories.GenreRepository;

import java.util.Collections;
import java.util.Map;

@Configuration
public class GenresStepConfig {

    private static final int CHUNK_SIZE = 5;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Map<String, Sort.Direction> sortsMap = Collections.singletonMap("id", Sort.Direction.ASC);

    @Bean
    public Step transformGenresStep(RepositoryItemReader<Genre> reader,
                                     GenreProcessor processor, MongoItemWriter<MongoGenre> writer) {
        return new StepBuilder("transformGenresStep", jobRepository)
                .<Genre, MongoGenre>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public RepositoryItemReader<Genre> genresItemReader() {
        RepositoryItemReader<Genre> reader = new RepositoryItemReader<>();
        reader.setName("genreReader");
        reader.setSort(sortsMap);
        reader.setRepository(genreRepository);
        reader.setMethodName("findAll");
        reader.setPageSize(CHUNK_SIZE);
        return reader;
    }

    @Bean
    public MongoItemWriter<MongoGenre> genreWriter() {
        MongoItemWriter<MongoGenre> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("genres");
        return writer;
    }
}






