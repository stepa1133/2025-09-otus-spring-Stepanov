package ru.otus.hw.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class LogFileHealthCheck implements HealthIndicator {
    private static final String LOG_FILE = "./app.log";

    private static final String SEARCH_TEXT = "Приложение запущено";

    @Override
    public Health health() {
        File logFile = new File(LOG_FILE);
        if (!logFile.exists()) {
            return Health.down().withDetail("file", LOG_FILE).withDetail("reason", "Лог-файл не найден").build();
        }
        try {
            boolean containsText = Files.lines(Paths.get(LOG_FILE)).anyMatch(line -> line.contains(SEARCH_TEXT));

            if (containsText) {
                return Health.up().withDetail("file", LOG_FILE).withDetail("message", "Запись найдена").build();
            } else {
                return Health.down().withDetail("file", LOG_FILE)
                        .withDetail("reason", "Запись 'Приложение запущено' не найдена")
                        .build();
            }
        } catch (IOException e) {
            return Health.down().withDetail("file", LOG_FILE)
                    .withDetail("reason", "Ошибка чтения файла: " + e.getMessage())
                    .build();
        }
    }
}
