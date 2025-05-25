package com.project.PriceComparator.dto;

import java.time.LocalDate;

public class PriceHistoryResponse {
    private String productId;
    private String productName;
    private String storeName;
    private String brand;
    private String category;
    private double price;
    private LocalDate date;

    public PriceHistoryResponse(String productId, String productName, String storeName,
                                String brand, String category, double price, LocalDate date) {
        this.productId = productId;
        this.productName = productName;
        this.storeName = storeName;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.date = date;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }
}
