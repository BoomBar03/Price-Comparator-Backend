package com.project.PriceComparator.dto;

public class BestValueResponse {
    private String productId;
    private String productName;
    private String storeName;
    private String brand;
    private String category;
    private double price;
    private double packageQuantity;
    private String packageUnit;
    private double valuePerUnit;

    public BestValueResponse(String productId, String productName, String storeName,
                             String brand, String category, double price,
                             double packageQuantity, String packageUnit, double valuePerUnit) {
        this.productId = productId;
        this.productName = productName;
        this.storeName = storeName;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.packageQuantity = packageQuantity;
        this.packageUnit = packageUnit;
        this.valuePerUnit = valuePerUnit;
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

    public double getPackageQuantity() {
        return packageQuantity;
    }

    public String getPackageUnit() {
        return packageUnit;
    }

    public double getValuePerUnit() {
        return valuePerUnit;
    }
}
