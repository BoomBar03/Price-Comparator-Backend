package com.project.PriceComparator.controller;

import com.project.PriceComparator.dto.BestDiscountResponse;
import com.project.PriceComparator.service.DiscountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/top")
    public List<BestDiscountResponse> getTopDiscounts(@RequestParam(defaultValue = "10") int limit) {
        return discountService.getTopDiscountsAllStores(limit);
    }
}
