package com.project.PriceComparator;

import com.project.PriceComparator.model.Product;
import com.project.PriceComparator.service.CsvService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvServiceTest {

    private final CsvService csvService = new CsvService();

    @Test
    void shouldParseCsvFileAndReturnCorrectProducts() {
        String fileName = "test_lidl.csv";
        String storeName = "Lidl";
        LocalDate date = LocalDate.of(2025, 5, 1);

        List<Product> result = csvService.readProductCsv(fileName, storeName, date);

        assertFalse(result.isEmpty(), "CSV-ul ar trebui să conțină produse");
        assertEquals("lapte zuzu", result.get(0).getProductName());
        assertEquals("Lidl", result.get(0).getStoreName());
        assertEquals(LocalDate.of(2025, 5, 1), result.get(0).getDate());
    }
}
