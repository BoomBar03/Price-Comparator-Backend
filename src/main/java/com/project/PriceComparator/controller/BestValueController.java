package com.project.PriceComparator.controller;

import com.project.PriceComparator.dto.BestValueResponse;
import com.project.PriceComparator.service.BestValueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class BestValueController {

    private final BestValueService bestValueService;

    public BestValueController(BestValueService bestValueService) {
        this.bestValueService = bestValueService;
    }

    @GetMapping
    public List<BestValueResponse> getRecommendations(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String brand
    ) {
        return bestValueService.getBestValueProducts(category, productName, brand);
    }
}
