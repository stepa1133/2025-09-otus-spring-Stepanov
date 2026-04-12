package ru.otus.hw.client.fallback;

import org.springframework.stereotype.Component;
import ru.otus.hw.client.TimeClient;
import ru.otus.hw.models.time.TimeResponse;

@Component
public class TimeClientFallback implements TimeClient {
    @Override
    public TimeResponse getTime() {
        System.out.println("🔥 FALLBACK СРАБОТАЛ");
        TimeResponse response = new TimeResponse();
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }

    @Override
    public TimeResponse getTimeCircuitBreaker() {
        return getTime();
    }

    @Override
    public void sendClearCircuitBreaker() {

    }

    @Override
    public TimeResponse getTimeSlow() {
        return getTime();
    }

    @Override
    public TimeResponse getTimeRetry() {
      //  return getTime();
        throw new RuntimeException("fallback error");

    }
}
