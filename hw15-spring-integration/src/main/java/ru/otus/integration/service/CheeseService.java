package ru.otus.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Product;
import ru.otus.integration.domain.ProductType;

import java.util.Map;

@Service
public class CheeseService {

    public Product produce(Map.Entry<ProductType, Integer> item) {

        System.out.println("Producing cheese: " + item.getValue());

        return new Product(ProductType.CHEESE, item.getValue());
    }

}
