package ru.otus.hw.service;

import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Student;

@Component
@Getter
@Profile("shell")
public class TestUserLoginContext implements LoginContext {
    private Student student;

    @Override
    public void login(Student student) {
        this.student = student;
    }

    @Override
    public void unregister() {
        this.student = null;
    }

    @Override
    public boolean isStudentSignIn() {
        return student != null;
    }
}
