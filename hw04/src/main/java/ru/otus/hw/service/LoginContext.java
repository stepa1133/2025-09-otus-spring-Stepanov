package ru.otus.hw.service;

import ru.otus.hw.domain.Student;


public interface LoginContext {
    void login(Student student);

    Student getStudent();

    boolean isStudentSignIn();

    void unregister();
}
