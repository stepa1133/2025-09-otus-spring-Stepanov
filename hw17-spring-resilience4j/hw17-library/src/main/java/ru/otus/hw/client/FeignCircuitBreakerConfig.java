package ru.otus.hw.client;

import feign.RequestInterceptor;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class FeignCircuitBreakerConfig {


//rename circuit breaker
    @Bean
    public CircuitBreakerNameResolver circuitBreakerNameResolver() {
        return (feignClientName, target, method) -> feignClientName; //timeApi
    }

    @Bean
    public feign.Retryer retryer() {
        return feign.Retryer.NEVER_RETRY;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template ->
                template.header("X-Request-ID", UUID.randomUUID().toString());
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoder() {
            private final ErrorDecoder defaultDecoder = new Default();

            @Override
            public Exception decode(String methodKey, Response response) {
                if (response.status() == 429) {
                    return new RuntimeException("Too Many Requests (429)");
                }
                return defaultDecoder.decode(methodKey, response);
            }
        };
    }
}
