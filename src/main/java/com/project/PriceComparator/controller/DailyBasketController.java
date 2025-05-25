package com.project.PriceComparator.controller;

import com.project.PriceComparator.dto.DailyBasketFullResponse;
import com.project.PriceComparator.dto.DailyBasketRequest;
import com.project.PriceComparator.dto.DailyBasketResponse;
import com.project.PriceComparator.service.DailyBasketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
public class DailyBasketController {

    private final DailyBasketService dailyBasketService;

    public DailyBasketController(DailyBasketService dailyBasketService) {
        this.dailyBasketService = dailyBasketService;
    }

    @PostMapping("/optimize")
    public DailyBasketFullResponse optimizeBasket(@RequestBody DailyBasketRequest request) {
        return dailyBasketService.optimizeBasket(request.getItems());
    }


}
