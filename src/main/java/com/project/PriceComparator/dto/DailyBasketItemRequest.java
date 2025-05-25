package com.project.PriceComparator.dto;

public class DailyBasketItemRequest {
    private String productName;
    private int quantity;

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
