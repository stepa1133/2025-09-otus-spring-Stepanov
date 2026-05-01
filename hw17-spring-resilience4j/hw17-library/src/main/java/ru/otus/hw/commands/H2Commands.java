package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import org.h2.tools.Console;

import java.sql.SQLException;

@RequiredArgsConstructor
@ShellComponent
public class H2Commands {
    @ShellMethod(value = "Start console DB", key = "db")
    public void startConsoleDB() throws SQLException {
        Console.main(new String[]{});
    }
}
