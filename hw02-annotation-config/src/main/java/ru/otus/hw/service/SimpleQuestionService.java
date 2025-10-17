package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Question;


@RequiredArgsConstructor
@Service
public class SimpleQuestionService implements QuestionService {

    private final IOService ioService;

    private final QuestionConverter questionConverter;

    @Override
    public void askQuestion(Question question) {
        String questionStr = questionConverter.convertQuestion(question);
        ioService.printFormattedLine(questionStr);
    }

    @Override
    public boolean checkAnswer(Question question) {
        String answerPrompt = "Please input your answer number:";
        String answerErrMessage = String.format("You have to choose a number from 0 to %d", question.answers().size());
        int answNum = ioService.readIntForRangeWithPrompt(1, question.answers().size(), answerPrompt, answerErrMessage);
        return question.answers().get(answNum - 1).isCorrect();

    }
}
