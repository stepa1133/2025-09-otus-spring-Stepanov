package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import java.util.Properties;

import static ru.otus.hw.config.JobConfig.COMMENTS_POSTFIX_NAME;
import static ru.otus.hw.config.JobConfig.COMMENTS_POSTFIX_VALUE;
import static ru.otus.hw.config.JobConfig.H2_TO_MONGO_JOB_NAME;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final Job transformH2ToMongoJob;

    private final JobLauncher jobLauncher;

    private final JobOperator jobOperator;

    private final JobExplorer jobExplorer;

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(H2_TO_MONGO_JOB_NAME));
    }

    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "jl")
    public void startMigrationJobWithJobLauncher() throws Exception {
        JobExecution execution = jobLauncher.run(transformH2ToMongoJob, new JobParametersBuilder()
                .addString(COMMENTS_POSTFIX_NAME, COMMENTS_POSTFIX_VALUE)
                .toJobParameters());
        System.out.println(execution);
    }

    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "jo")
    public void startMigrationJobWithJobOperator() throws Exception {
        Properties properties = new Properties();
        properties.put(COMMENTS_POSTFIX_NAME, COMMENTS_POSTFIX_VALUE);

        Long executionId = jobOperator.start(H2_TO_MONGO_JOB_NAME, properties);
        System.out.println(jobOperator.getSummary(executionId));
    }
}
