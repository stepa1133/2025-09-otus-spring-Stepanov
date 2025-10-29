package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionConverterTest {
    QuestionConverter questionConverter;
    @BeforeEach
    void setUp() {
        questionConverter = mock(QuestionToStringConverter.class);
    }

    @Test
    void testConvertQuestion() {
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
        List<Question> questions = List.of(question1, question2, question3);


        questions.forEach(question -> {
            when(questionConverter.convertQuestion(question)).thenCallRealMethod();
            String convertResult = questionConverter.convertQuestion(question);
            assertTrue(convertResult.matches(".*\\?\n(\\d{1,2}\\. .*\n){1,10}"));
        });

    }

}
