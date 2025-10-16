package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;        // подразумевается  @Autowired

    private final StudentService studentService; // подразумевается  @Autowired

    private final ResultService resultService;   // подразумевается  @Autowired

    @Override
    public void run() {
        var student = studentService.determineCurrentStudent();
        var testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }
}
