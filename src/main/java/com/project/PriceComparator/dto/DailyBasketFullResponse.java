package com.project.PriceComparator.dto;

import java.util.List;

public class DailyBasketFullResponse {
    private List<DailyBasketResponse> items;
    private double total;

    public DailyBasketFullResponse(List<DailyBasketResponse> items, double total) {
        this.items = items;
        this.total = total;
    }

    public List<DailyBasketResponse> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }
}
