package ru.otus.integration.domain;

import java.util.Map;

public class Order {

    private String storeName;
    private Map<ProductType, Integer> items;

    public Order(String storeName, Map<ProductType, Integer> items) {
        this.storeName = storeName;
        this.items = items;
    }

    public String getStoreName() {
        return storeName;
    }

    public Map<ProductType, Integer> getItems() {
        return items;
    }
}