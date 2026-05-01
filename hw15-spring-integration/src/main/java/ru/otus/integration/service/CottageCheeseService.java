package ru.otus.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Product;
import ru.otus.integration.domain.ProductType;

import java.util.Map;

@Service
public class CottageCheeseService {

    public Product produce(Map.Entry<ProductType, Integer> item) {

        System.out.println("Producing cottage cheese: " + item.getValue());

        return new Product(ProductType.COTTAGE_CHEESE, item.getValue());
    }

}