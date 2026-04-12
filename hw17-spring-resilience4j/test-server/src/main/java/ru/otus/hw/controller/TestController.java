package ru.otus.hw.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.client.TimeResponse;
import ru.otus.hw.service.TimeService;

import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TimeService timeService;

    private AtomicInteger counter = new AtomicInteger(0);


    @GetMapping("/getTime")
    public ResponseEntity<TimeResponse> getTime() {
        return ResponseEntity.status(OK).body(timeService.getCurrentTime());
    }

    // ❌ всегда 500
    @GetMapping("/error")
    public ResponseEntity<String> error() {
        return ResponseEntity.status(500).body("Internal error");
    }

    // ⏳ долгий ответ (для TimeLimiter)
    @GetMapping("/getTimeSlow")
    public ResponseEntity<TimeResponse> slow() throws InterruptedException {
        Thread.sleep(5000);
        return ResponseEntity.status(OK).body(timeService.getCurrentTime());
    }

    @GetMapping("/getTimeRetry")
    public ResponseEntity<TimeResponse> getTimeRetry() {
        int attempt = counter.incrementAndGet();
        System.out.println("New request #" + attempt);

        if (attempt <= 4) {
            throw new RuntimeException("Fail attempt " + attempt);
        }

        return ResponseEntity.status(OK).body(timeService.getCurrentTime());
    }


    @GetMapping("/circuitbreaker")
    public ResponseEntity<TimeResponse> circuitBreaker() {
        int attempt = counter.incrementAndGet();
        System.out.println("New request #" + attempt);

        if (attempt <= 4) {
            throw new RuntimeException("Fail attempt " + attempt);
        }

        return ResponseEntity.status(OK).body(timeService.getCurrentTime());
    }

    @GetMapping("/circuitbreaker/clear")
    public void circuitBreakerClear() {
        counter = new AtomicInteger();
        System.out.println("counter clear");
    }

    // 🎯 кастомный статус
    @GetMapping("/status")
    public ResponseEntity<String> status(@RequestParam int code) {
        return ResponseEntity.status(code).body("Status: " + code);
    }
}