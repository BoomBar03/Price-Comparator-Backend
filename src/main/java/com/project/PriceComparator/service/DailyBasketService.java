package com.project.PriceComparator.service;

import com.project.PriceComparator.dto.DailyBasketFullResponse;
import com.project.PriceComparator.dto.DailyBasketItemRequest;
import com.project.PriceComparator.dto.DailyBasketResponse;
import com.project.PriceComparator.model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DailyBasketService {

    private final CsvService csvService;

    public DailyBasketService(CsvService csvService) {
        this.csvService = csvService;
    }

    public DailyBasketFullResponse optimizeBasket(List<DailyBasketItemRequest> items) {
        List<Product> allProducts = new ArrayList<>();
        LocalDate date = LocalDate.of(2025, 5, 1);

        allProducts.addAll(csvService.readProductCsv("lidl_2025-05-01.csv", "Lidl", date));
        allProducts.addAll(csvService.readProductCsv("profi_2025-05-01.csv", "Profi", date));
        allProducts.addAll(csvService.readProductCsv("kaufland_2025-05-01.csv", "Kaufland", date));

        List<DailyBasketResponse> result = new ArrayList<>();
        double total = 0.0;

        for (DailyBasketItemRequest item : items) {
            Optional<Product> cheapest = allProducts.stream()
                    .filter(p -> p.getProductName().equalsIgnoreCase(item.getProductName()))
                    .min(Comparator.comparingDouble(Product::getPrice));

            if (cheapest.isPresent()) {
                Product p = cheapest.get();
                DailyBasketResponse response = new DailyBasketResponse(
                        p.getProductName(),
                        p.getStoreName(),
                        p.getPrice(),
                        item.getQuantity()
                );
                result.add(response);
                total += response.getTotalPrice();
            }
        }

        return new DailyBasketFullResponse(result, total);
    }


}
