package com.project.PriceComparator.dto;

import java.util.List;

public class DailyBasketRequest {
    private List<DailyBasketItemRequest> items;

    public List<DailyBasketItemRequest> getItems() {
        return items;
    }

    public void setItems(List<DailyBasketItemRequest> items) {
        this.items = items;
    }
}
