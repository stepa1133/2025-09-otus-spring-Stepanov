package ru.otus.hw.service;

import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.stream.IntStream;


@Component
public class QuestionToStringConverter implements QuestionConverter {
    @Override
    public String convertQuestion(Question question) {
        StringBuilder sb = new StringBuilder();

        sb.append(question.text()).append("\n");

        IntStream.range(0, question.answers().size())
                .forEach(i -> sb.append(i + 1)
                        .append(". ")
                        .append(converAnswerToString(question.answers().get(i))));
        return sb.toString();
    }

    private String converAnswerToString(Answer answer) {
        return String.format("%s\n", answer.text());
    }
}
