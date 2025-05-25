package com.project.PriceComparator.dto;

/*
 * DailyBasketItemRequest reprezintă un singur produs cerut de client,
 * incluzând numele produsului și cantitatea dorită.
 * Este un element individual din lista din DailyBasketRequest.
 */


public class DailyBasketItemRequest {
    private String productName;
    private int quantity;

    public DailyBasketItemRequest(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }


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
