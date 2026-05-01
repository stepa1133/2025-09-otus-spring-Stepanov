package ru.otus.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Product;
import ru.otus.integration.domain.ProductType;

import java.util.Map;

@Service
public class MilkService {

    public Product produce(Map.Entry<ProductType, Integer> item) {

        System.out.println("Producing milk: " + item.getValue());

        return new Product(ProductType.MILK, item.getValue());
    }

}
