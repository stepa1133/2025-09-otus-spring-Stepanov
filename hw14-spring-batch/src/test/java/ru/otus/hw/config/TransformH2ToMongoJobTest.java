package ru.otus.hw.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.otus.hw.config.JobConfig.*;


@SpringBootTest
@SpringBatchTest
public class TransformH2ToMongoJobTest {


    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;


    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void donla() throws Exception{
        System.out.println("TEST STARTED");

        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(H2_TO_MONGO_JOB_NAME);

        JobParameters parameters = new JobParametersBuilder()
                .addString(COMMENTS_POSTFIX_NAME, COMMENTS_POSTFIX_VALUE)
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);
        Assertions.assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

    }

}
