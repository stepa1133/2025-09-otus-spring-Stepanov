package ru.otus.hw.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class QuestionDaoIntegrationTest {


    private CsvQuestionDao questionDao;
    private TestFileNameProvider fileNameProvider;

    @BeforeEach
    void setUp() {
        fileNameProvider = mock();
        questionDao = new CsvQuestionDao(fileNameProvider);
    }

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
    void shouldThrowCustomException() {

        given(fileNameProvider.getTestFileName()).willReturn("questions2.csv");
        assertThatThrownBy(() -> questionDao.findAll()).isInstanceOf(QuestionReadException.class);

        given(fileNameProvider.getTestFileName()).willReturn("questions3.csv");
        assertThatThrownBy(() -> questionDao.findAll()).isInstanceOf(QuestionReadException.class);

    }
}
