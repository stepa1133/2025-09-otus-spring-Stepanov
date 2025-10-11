package ru.otus.hw.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class TestServiceImplTest {

    private TestServiceImpl testService;
    private IOService ioService;
    private QuestionDao csvQuestionDao;
    private QuestionConverter questionConverter;
    private InOrder inOrder;

    @BeforeEach
    void setUp() {
        ioService = mock(StreamsIOService.class);
        csvQuestionDao = mock(CsvQuestionDao.class);
        questionConverter = mock(QuestionToStringConverter.class);
        inOrder = inOrder(csvQuestionDao, questionConverter);
        testService = new TestServiceImpl(ioService, csvQuestionDao, questionConverter);
    }


    @DisplayName("Should print")
    @Test
    void testExecuteTest() {

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

        testService.executeTest();

        var captor = ArgumentCaptor.forClass(Question.class);
        inOrder.verify(csvQuestionDao , times(1)).findAll();
        inOrder.verify(questionConverter , times(expected.size())).convertQuestionToString(captor.capture());

        var actual = csvQuestionDao.findAll();
        assertThat(actual).isEqualTo(expected);

    }





}
