package ru.otus.hw;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.otus.hw.service.TestRunnerService;

@Component
@RequiredArgsConstructor
@Profile("handConsole")
public class ConsoleTestApplicationRunner implements ApplicationRunner {

    private final ApplicationContext context;

    @Override
    public void run(ApplicationArguments args) {
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();
    }
}
