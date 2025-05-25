package com.project.PriceComparator.dto;

import java.util.List;

/*
 * DailyBasketRequest reprezintă un coș de cumpărături transmis de client.
 * Conține o listă de DailyBasketItemRequest, adică produsele dorite și cantitățile acestora.
 * Este folosit ca input pentru a calcula prețurile pentru fiecare produs în funcție de magazin.
 */


public class DailyBasketRequest {
    private List<DailyBasketItemRequest> items;

    public List<DailyBasketItemRequest> getItems() {
        return items;
    }

    public void setItems(List<DailyBasketItemRequest> items) {
        this.items = items;
    }
}
