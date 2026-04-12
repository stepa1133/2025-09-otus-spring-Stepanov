package ru.otus.hw.services.time;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.client.TimeClient;
import ru.otus.hw.models.time.TimeResponse;


import java.sql.Time;


@Service
@Slf4j
@RequiredArgsConstructor
public class TimeService {

    private final CircuitBreakerRegistry registry;

    private final TimeClient timeClient;
    public String getCurrentTime() {
            TimeResponse time = timeClient.getTime();
            return new Time(time.getTimestamp()).toString();
    }

    @Retry(name = "timeApi")
    public String getTimeRetry() {

        TimeResponse time = timeClient.getTimeRetry();
        return new Time(time.getTimestamp()).toString();
    }

    public String getTimeSlow() {
        CircuitBreaker cb = registry.circuitBreaker("timeApi");
        TimeResponse time = null;

        for (int i = 0; i < 15; i++) {
            time = timeClient.getTimeSlow();
            log.info("[Time limiter] \uD83D\uDD01 new attempt {}, {}", i+1, cb.getState());

        }
        return new Time(time.getTimestamp()).toString();
    }

    public String getTimeCircuitBreaker() throws Exception {
        TimeResponse time = null;
        timeClient.sendClearCircuitBreaker();
        CircuitBreaker cb = registry.circuitBreaker("timeApi");
        boolean stateOpen = false;
        int sendedRequestToOtherService = 0;
// 1
// Посылаем запросы, до тех пор пока state не сменится CLOSED -> OPEN
        for (int i = 0; i < 10; i++) {
            if (cb.getState() == CircuitBreaker.State.OPEN) {
                log.info("[circuit breaker] is OPEN → stop calling. Count attempts is {}", i);
                stateOpen = true;
                break;
            }

            time = timeClient.getTimeCircuitBreaker();
            sendedRequestToOtherService++;
            log.info("[circuit breaker] \uD83D\uDD01 new attempt {}", sendedRequestToOtherService);
        }
// 2
// Ждём, до тех пор пока state не сменится когда OPEN -> HALF_OPEN
// Все запросы в этом стейте не будут отправляться в сторонний сервис,
// а сразу отработает логика fallback (до истечении времени waitDurationInOpenState)

        if (stateOpen) {
            Thread.sleep(2000);
            time = timeClient.getTimeCircuitBreaker();
            sendedRequestToOtherService++;
            log.info("[circuit breaker] \uD83D\uDD01 new attempt {}", sendedRequestToOtherService);
            if (cb.getState() == CircuitBreaker.State.HALF_OPEN) {
                log.info("[circuit breaker]!!! wait HALF_OPEN, current State HALF_OPEN");
            }
        }

// 3
// Считаем кол-во успешных запросов для перехода HALF_OPEN -> CLOSED
        int attemptsHalfOpenToClosed = 1;
        while (cb.getState() == CircuitBreaker.State.HALF_OPEN) {
            sendedRequestToOtherService++;
            attemptsHalfOpenToClosed ++;
            log.info("[circuit breaker] \uD83D\uDD01 new attempt {}, cur state {}", sendedRequestToOtherService, cb.getState());
            time = timeClient.getTimeCircuitBreaker();
            Thread.sleep(500);
        }
        log.info("[circuit breaker] after {} requests state changed from HALF_OPEN to {}", attemptsHalfOpenToClosed, cb.getState());


        return new Time(time.getTimestamp()).toString();
    }



    }