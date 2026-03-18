package ru.otus.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.integration.domain.Order;
import ru.otus.integration.domain.Product;
import ru.otus.integration.domain.ProductType;
import ru.otus.integration.gateway.FarmGateway;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class IntegrationFlowIT {

    @Autowired
    private FarmGateway gateway;

    @Test
    void shouldProcessOrder() {
        Order order = new Order("Test order", Map.of(
                ProductType.MILK, 2,
                ProductType.CHEESE, 1,
                ProductType.COTTAGE_CHEESE, 1
        ));

        List<Product> result = gateway.makeOrder(order);

        assertNotNull(result);
        assertEquals(3, result.size());
    }
}
