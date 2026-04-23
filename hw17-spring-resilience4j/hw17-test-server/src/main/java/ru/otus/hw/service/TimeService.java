package ru.otus.hw.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.client.TimeClient;
import ru.otus.hw.client.TimeResponse;

import java.sql.Time;

@RequiredArgsConstructor
@Service
public class TimeService {

    private final TimeClient timeClient;

    public TimeResponse getCurrentTime() {
        TimeResponse time = timeClient.getTime("Europe/Moscow", "dd/MM/yyyy");
        return time;
    }
}
