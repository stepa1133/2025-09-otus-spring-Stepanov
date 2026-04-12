package ru.otus.hw.client;

import lombok.Data;

@Data
public class TimeResponse {
    private String timezone;
    private String formatted;
    private long timestamp;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
}
