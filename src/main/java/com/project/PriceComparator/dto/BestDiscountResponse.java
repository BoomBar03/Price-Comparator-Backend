package com.project.PriceComparator.dto;

public class BestDiscountResponse {
    private String productName;
    private String storeName;
    private double oldPrice;
    private double newPrice;
    private double discountPercent;

    public BestDiscountResponse(String productName, String storeName,
                                double oldPrice, double newPrice, double discountPercent) {
        this.productName = productName;
        this.storeName = storeName;
        this.oldPrice = Math.round(oldPrice * 100.0) / 100.0;
        this.newPrice = Math.round(newPrice * 100.0) / 100.0;
        this.discountPercent = discountPercent;
    }

    public String getProductName() {
        return productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }
}
