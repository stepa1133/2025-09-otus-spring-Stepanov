package ru.otus.hw.config.comments;

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
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.mongo.MongoComment;
import ru.otus.hw.repositories.CommentRepository;

import java.util.Collections;
import java.util.Map;

@Configuration
public class CommentsStepConfig {
    private static final int CHUNK_SIZE = 5;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Map<String, Sort.Direction> sortsMap = Collections.singletonMap("id", Sort.Direction.ASC);

    @Bean
    public Step transformCommentsStep(RepositoryItemReader<Comment> reader,
                                     CommentProcessor processor, MongoItemWriter<MongoComment> writer) {
        return new StepBuilder("transformCommentStep", jobRepository)
                .<Comment, MongoComment>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public RepositoryItemReader<Comment> commentItemReader() {
        RepositoryItemReader<Comment> reader = new RepositoryItemReader<>();
        reader.setName("commentReader");
        reader.setSort(sortsMap);
        reader.setRepository(commentRepository);
        reader.setMethodName("findAll");
        reader.setPageSize(CHUNK_SIZE);
        return reader;
    }

    @Bean
    public MongoItemWriter<MongoComment> commentWriter() {
        MongoItemWriter<MongoComment> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("comments");
        return writer;
    }

}
