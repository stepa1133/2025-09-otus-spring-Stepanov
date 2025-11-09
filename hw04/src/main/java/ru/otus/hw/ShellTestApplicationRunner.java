package ru.otus.hw;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("shell")
@RequiredArgsConstructor
public class ShellTestApplicationRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
    }
}
