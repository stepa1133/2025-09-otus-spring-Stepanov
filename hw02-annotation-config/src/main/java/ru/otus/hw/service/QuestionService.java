package ru.otus.hw.service;

import ru.otus.hw.domain.Question;

public interface QuestionService {
    void askQuestion(Question question);

    boolean checkAnswer(Question question);
}
