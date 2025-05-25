package com.project.PriceComparator.dto;

/*
 * DailyBasketResponse reprezintă răspunsul sistemului pentru un singur produs.
 * Include informații despre magazinul selectat, prețul unitar, cantitatea,
 * și prețul total (unitPrice * quantity).
 */


public class DailyBasketResponse {
    private String productName;
    private String storeName;
    private double unitPrice;
    private int quantity;
    private double totalPrice;

    public DailyBasketResponse(String productName, String storeName, double unitPrice, int quantity) {
        this.productName = productName;
        this.storeName = storeName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = unitPrice * quantity;
    }

    public String getProductName() {
        return productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getPrice() { return totalPrice; }

}
