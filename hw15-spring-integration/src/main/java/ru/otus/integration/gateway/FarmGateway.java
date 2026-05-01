package ru.otus.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.integration.domain.Order;
import ru.otus.integration.domain.Product;

import java.util.List;

@MessagingGateway
public interface FarmGateway {

    @Gateway(requestChannel = "orderChannel", replyChannel = "resultChannel")
    List<Product> makeOrder(Order order);

}
