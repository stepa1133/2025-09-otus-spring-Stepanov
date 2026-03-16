package ru.otus.integration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Order;
import ru.otus.integration.domain.Product;
import ru.otus.integration.domain.ProductType;
import ru.otus.integration.gateway.FarmGateway;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    
    private final FarmGateway farmGateway;

    @Override
    public void generateOrders() {
        Map<ProductType, Integer> orderItems = Map.of(
                ProductType.MILK, 5,
                ProductType.COTTAGE_CHEESE,20,
                ProductType.CHEESE, 10
        );
        Order order = new Order("MAGNIT", orderItems);
        List<Product> milkProduct = farmGateway.makeOrder(order);
    }
}
