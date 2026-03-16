package ru.otus.integration.domain;

public class Product {

    private ProductType type;
    private int quantity;

    public Product(ProductType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public ProductType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return type + " x" + quantity;
    }
}
