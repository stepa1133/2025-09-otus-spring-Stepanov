package ru.otus.integration.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.integration.service.OrderService;

@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {

    private final OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        orderService.generateOrders();
    }
}
