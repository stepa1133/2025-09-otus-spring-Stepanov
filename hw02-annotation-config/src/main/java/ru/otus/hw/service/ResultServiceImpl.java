package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.domain.TestResult;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final IOService ioService;

    @Override
    public void showResult(TestResult testResult) {
        if (testResult != null)  {
            ioService.printLine("");
            ioService.printLine("Test results: ");
            ioService.printFormattedLine("Student: %s", testResult.getStudent().getFullName());
            ioService.printFormattedLine("Answered questions count: %d", testResult.getAnsweredQuestions().size());
            ioService.printFormattedLine("Right answers count: %d", testResult.getRightAnswersCount());

            if (testResult.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
                ioService.printLine("Congratulations! You passed test!");
                return;
            }
            ioService.printLine("Sorry. You fail test.");
        } else {
            ioService.printLine("Something wrong. Please contact test administrator");
        }
    }
}
