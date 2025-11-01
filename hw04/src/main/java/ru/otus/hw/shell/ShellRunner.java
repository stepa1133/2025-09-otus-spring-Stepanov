package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.CommandAvailability;
import ru.otus.hw.service.LocalizedMessagesService;
import ru.otus.hw.service.LoginContext;
import ru.otus.hw.service.ResultService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestService;

@Profile("shell")
@Command(group = "Test application commands")
@RequiredArgsConstructor
public class ShellRunner {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final LoginContext loginContext;

    private final LocalizedMessagesService messagesService;

    @Command(description = "Test sign in", command = "greeting", alias = {"login", "auth"})
    public String greeting() {
        loginContext.login(studentService.determineCurrentStudent());
        return messagesService.getMessage("Shell.success.sign.info", loginContext.getStudent().getFullName());
    }

    @Command(description = "Start test application", command = "start", alias = {"run", "go"})
    @CommandAvailability(provider = "shellAvailabilityProvider")
    public void run() {
        var testResult = testService.executeTestFor(loginContext.getStudent());
        resultService.showResult(testResult);
    }

    @Command(description = "Log out", command = "logout")
    public String logout() {
        loginContext.unregister();
        return messagesService.getMessage("Shell.logout.success.info");
    }

}


