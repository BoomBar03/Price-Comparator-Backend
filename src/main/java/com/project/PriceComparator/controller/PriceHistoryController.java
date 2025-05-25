package com.project.PriceComparator.controller;

import com.project.PriceComparator.dto.PriceHistoryResponse;
import com.project.PriceComparator.service.PriceHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/price-history")
public class PriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    public PriceHistoryController(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    @GetMapping
    public List<PriceHistoryResponse> getPriceHistory(
            @RequestParam(required = false) String store,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand
    ) {
        return priceHistoryService.getPriceHistory(store, category, brand);
    }
}
