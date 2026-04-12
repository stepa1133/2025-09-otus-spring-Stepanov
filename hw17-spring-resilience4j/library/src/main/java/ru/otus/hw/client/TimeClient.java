package ru.otus.hw.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.client.fallback.TimeClientFallback;
import ru.otus.hw.models.time.TimeResponse;


@FeignClient(name = "timeApi", url = "http://127.0.0.1:8084/test/",
        fallback = TimeClientFallback.class, configuration = FeignCircuitBreakerConfig.class)
public interface TimeClient {

    @GetMapping("/getTime")
    TimeResponse getTime();

    @GetMapping("/circuitbreaker")
    TimeResponse getTimeCircuitBreaker();

    @GetMapping("/getTimeRetry")
    TimeResponse getTimeRetry();


    @GetMapping("/circuitbreaker/clear")
    void sendClearCircuitBreaker();

    @GetMapping("/getTimeSlow")
    TimeResponse getTimeSlow();
}