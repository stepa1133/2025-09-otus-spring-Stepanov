package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.exceptions.QuestionReadException;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    private final QuestionService questionService;


    @Override
    public TestResult executeTestFor(Student student) {
        try {
            ioService.printLine("");
            ioService.printFormattedLine("Please answer the questions below%n");
            var questions = questionDao.findAll();
            var testResult = new TestResult(student);

            for (var question: questions) {
                questionService.askQuestion(question);
                var isAnswerValid = questionService.checkAnswer(question);
                testResult.applyAnswer(question, isAnswerValid);
            }
            return testResult;
        } catch (QuestionReadException e) {
            return null;
        }
    }

}
