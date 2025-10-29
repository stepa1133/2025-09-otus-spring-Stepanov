package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Question;

@Service
@RequiredArgsConstructor
public class SimpleQuestionService implements QuestionService {

    private final LocalizedIOService ioService;

    private final QuestionConverter questionConverter;

    @Override
    public void askQuestion(Question question) {
        String questionStr = questionConverter.convertQuestion(question);
        ioService.printFormattedLine(questionStr);
    }

    @Override
    public boolean checkAnswer(Question question) {
        int answNum = ioService.readIntForRangeWithPromptLocalized(1, question.answers().size(),
                            "QuestionService.ask.answer", "QuestionService.ask.answer.error");
        return question.answers().get(answNum - 1).isCorrect();
    }
}
