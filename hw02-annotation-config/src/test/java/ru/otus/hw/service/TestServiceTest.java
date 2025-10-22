package ru.otus.hw.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class TestServiceTest {
    private TestServiceImpl testService;
    private IOService ioService;
    private QuestionDao csvQuestionDao;
    private QuestionService questionService;

    private InOrder inOrder;

    @BeforeEach
    void setUp() {
        ioService = mock(StreamsIOService.class);
        csvQuestionDao = mock(CsvQuestionDao.class);
        questionService = mock(SimpleQuestionService.class);
        inOrder = inOrder(ioService, csvQuestionDao, questionService);
        testService = new TestServiceImpl(ioService, csvQuestionDao, questionService);
    }


    @Test
    void testExecuteTestFor() {

        Question question1 = new Question("Is there life on Mars?", List.of(new Answer("Science doesn't know this yet", true),
                new Answer("Certainly. The red UFO is from Mars. And green is from Venus", false),
                new Answer("Absolutely not", false)));
        Question question2 = new Question("How should resources be loaded form jar in Java?", List.of(new Answer("ClassLoader#geResourceAsStream or ClassPathResource#getInputStream", true),
                new Answer("ClassLoader#geResource#getFile + FileReader", false),
                new Answer("Wingardium Leviosa", false)));
        Question question3 = new Question("Which option is a good way to handle the exception?", List.of(new Answer("@SneakyThrow", false),
                new Answer("e.printStackTrace()", false),
                new Answer("Rethrow with wrapping in business exception (for example, QuestionReadException)", true),
                new Answer("Ignoring exception", false)   ));

        List<Question> expected = List.of(question1, question2, question3);
        given(csvQuestionDao.findAll()).willReturn(expected);
        given(questionService.checkAnswer(any())).willReturn(true);

        Student student = new Student("Kurt", "Cobain");
        TestResult result = testService.executeTestFor(student);


        inOrder.verify(ioService, times(1)).printLine("");
        inOrder.verify(ioService, times(1)).printFormattedLine("Please answer the questions below%n");
        inOrder.verify(csvQuestionDao , times(1)).findAll();
        for (Question question: expected) {
            inOrder.verify(questionService , times(1)).askQuestion(question);
            inOrder.verify(questionService , times(1)).checkAnswer(question);
        }

        assertThat(result.getStudent().getFullName()).isEqualTo("Kurt Cobain");
        assertThat(result.getAnsweredQuestions().size()).isEqualTo(3);
        assertThat(result.getRightAnswersCount()).isEqualTo(3);

    }

}
