package ru.otus.hw.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

    public static final String H2_TO_MONGO_JOB_NAME = "transformH2ToMongoJob";

    private static final int CHUNK_SIZE = 5;

    private final Logger logger = LoggerFactory.getLogger("Batch");

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobExplorer jobExplorer; // Добавьте это поле
    @Bean
    public Job transformH2ToMongoJob(Step transformAuthorsStep, Step transformGenresStep,
                                                          Step transformCommentsStep, Step transformBooksStep) {
        return new JobBuilder(H2_TO_MONGO_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorsStep)
                .next(transformGenresStep)
                .next(transformCommentsStep)
                .next(transformBooksStep)
                .end()
                .build();
    }




}
