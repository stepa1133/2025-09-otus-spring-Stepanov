package ru.otus.hw;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw.services.time.TimeService;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {
//   http://localhost:8081/actuator/circuitbreakers - посмотреть текущее состояние CurcuitBreaker

    private final TimeService timeService;
    private final CircuitBreakerRegistry registry;
    private final RetryRegistry retryRegistry;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== RUNNER STARTED ===");

        registry.getAllCircuitBreakers()
                .forEach(cb -> System.out.println(cb.getName()));
        CircuitBreaker cb = registry.circuitBreaker("timeApi");
        cb.getEventPublisher()
                .onStateTransition(event ->
                        log.info("[circuit breaker] STATE CHANGE: {}", event.getStateTransition()))
                .onError(event -> {
                    Throwable t = event.getThrowable();
                    log.error("[circuit breaker] ERROR | name={} | state={} | type={} | message={}",
                            event.getCircuitBreakerName(),
                            cb.getState(),
                            t.getClass().getSimpleName(),
                            t.getMessage());
                })
                .onSuccess(event ->
                        log.info("[circuit breaker] SUCCESS"));

/*        RetryRegistry registry = (RetryRegistry) retryRegistry.retry("timeApi");
        registry.retry("timeApi")
                .getEventPublisher()
                .onRetry(event ->
                        log.info("RETRY attempt: " + event.getNumberOfRetryAttempts()))
                .onError(event ->
                        log.info("ERROR: " + event.getLastThrowable()))
                .onSuccess(event ->
                        log.info("SUCCESS after retry"));*/

    }
}

