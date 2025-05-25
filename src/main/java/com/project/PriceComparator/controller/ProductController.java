package com.project.PriceComparator.controller;

import com.project.PriceComparator.model.Product;
import com.project.PriceComparator.service.CsvService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final CsvService csvService;

    public ProductController(CsvService csvService) {
        this.csvService = csvService;
    }

    @GetMapping("/lidl")
    public List<Product> getLidlProducts() {
        return csvService.readProductCsv(
                "src/main/resources/data/lidl_2025-05-01.csv",
                "Lidl",
                LocalDate.of(2025, 5, 8)
        );
    }
}
