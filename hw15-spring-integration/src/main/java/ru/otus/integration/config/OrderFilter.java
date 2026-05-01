package ru.otus.integration.config;

import org.springframework.stereotype.Component;
import ru.otus.integration.domain.Order;
import ru.otus.integration.domain.ProductType;

import java.util.Map;

@Component
public class OrderFilter {

    public boolean filter(Order order) {
        if (order.getStoreName() == null || order.getStoreName().isEmpty()
                                         || "DIXY".equalsIgnoreCase(order.getStoreName())) {
            return false;
        }
        Map<ProductType, Integer> items = order.getItems();
        return !items.isEmpty();
    }
}
