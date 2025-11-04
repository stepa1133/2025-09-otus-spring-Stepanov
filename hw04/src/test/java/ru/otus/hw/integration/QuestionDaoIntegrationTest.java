package ru.otus.hw.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ContextConfiguration(classes = CsvQuestionDao.class)
public class QuestionDaoIntegrationTest {


    @Autowired
    private QuestionDao questionDao;
    @MockitoBean
    private TestFileNameProvider fileNameProvider;

    @Test
    void shouldContainCorrectData() {
        InputStreamReader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("questions.csv"));
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> lines = bufferedReader.lines().toList();
        int countAcceptableCvsQuestions = 0;
        for (int i = 1; i < lines.size(); i++) {
            assertTrue(lines.get(i).matches(".*\\?;(.*\\%(true|false)){2,10}"));
            if (lines.get(i).matches(".*\\?;(.*\\%(true|false)){2,10}")) {
                countAcceptableCvsQuestions++;
            }
        }

        given(fileNameProvider.getTestFileName()).willReturn("questions.csv");
        List<Question> questions = questionDao.findAll();
        assertThat(questions).hasSize(countAcceptableCvsQuestions);
    }

    @Test
    void shouldCorrectCreateQuestionsFromData() {
        Question question1 = new Question("Is there life on Mars?", List.of(new Answer("Science doesn't know this yet", true),
                new Answer("Certainly. The red UFO is from Mars. And green is from Venus", false),
                new Answer("Absolutely not", false)));
        Question question2 = new Question("How should resources be loaded form jar in Java?", List.of(new Answer("ClassLoader#geResourceAsStream or ClassPathResource#getInputStream", true),
                new Answer("ClassLoader#geResource#getFile + FileReader", false),
                new Answer("Wingardium Leviosa", false)));
        Question question3 = new Question("Which option is a good way to handle the exception?", List.of(new Answer("@SneakyThrow", false),
                new Answer("e.printStackTrace()", false),
                new Answer("Rethrow with wrapping in business exception (for example, QuestionReadException)", true),
                new Answer("Ignoring exception", false)));

        List<Question> expectedQuestions = List.of(question1, question2, question3);

        given(fileNameProvider.getTestFileName()).willReturn("questions.csv");
        List<Question> questions = questionDao.findAll();

        assertThat(expectedQuestions).isEqualTo(questions);
    }


    @Test
    void shouldThrowCustomExceptionCauseNoFile() {
        given(fileNameProvider.getTestFileName()).willReturn("questions2.csv");
        assertThatThrownBy(() -> questionDao.findAll())
                .isInstanceOf(QuestionReadException.class)
                .hasMessage("Something wrong in questions read");
    }

    @Test
    void shouldThrowCustomExceptionCauseIncorrectFileContent() {
        given(fileNameProvider.getTestFileName()).willReturn("questions3.csv");
        assertThatThrownBy(() -> questionDao.findAll())
                .isInstanceOf(QuestionReadException.class)
                .hasMessage("Something wrong in questions read");

    }
}
