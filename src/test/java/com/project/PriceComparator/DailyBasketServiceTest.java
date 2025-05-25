package com.project.PriceComparator;

import com.project.PriceComparator.dto.DailyBasketFullResponse;
import com.project.PriceComparator.dto.DailyBasketItemRequest;
import com.project.PriceComparator.dto.DailyBasketResponse;
import com.project.PriceComparator.model.Product;
import com.project.PriceComparator.service.CsvService;
import com.project.PriceComparator.service.DailyBasketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DailyBasketServiceTest {

    private CsvService csvService;
    private DailyBasketService dailyBasketService;

    @BeforeEach
    void setUp() {
        csvService = mock(CsvService.class);
        dailyBasketService = new DailyBasketService(csvService);
    }

    @Test
    void shouldReturnCheapestProductFromAllStores() {
        // Given
        Product lidlProduct = new Product("P001", "lapte zuzu", "lactate", "Zuzu", 1, "l", 9.90, "RON", "Lidl", LocalDate.of(2025, 5, 1));
        Product profiProduct = new Product("P001", "lapte zuzu", "lactate", "Zuzu", 1, "l", 10.90, "RON", "Profi", LocalDate.of(2025, 5, 1));
        Product kauflandProduct = new Product("P001", "lapte zuzu", "lactate", "Zuzu", 1, "l", 11.50, "RON", "Kaufland", LocalDate.of(2025, 5, 1));

        when(csvService.readProductCsv("lidl_2025-05-01.csv", "Lidl", LocalDate.of(2025, 5, 1)))
                .thenReturn(List.of(lidlProduct));
        when(csvService.readProductCsv("profi_2025-05-01.csv", "Profi", LocalDate.of(2025, 5, 1)))
                .thenReturn(List.of(profiProduct));
        when(csvService.readProductCsv("kaufland_2025-05-01.csv", "Kaufland", LocalDate.of(2025, 5, 1)))
                .thenReturn(List.of(kauflandProduct));

        DailyBasketItemRequest request = new DailyBasketItemRequest("lapte zuzu", 2);

        // When
        DailyBasketFullResponse result = dailyBasketService.optimizeBasket(List.of(request));

        // Then
        assertNotNull(result);
        assertEquals(1, result.getItems().size());

        DailyBasketResponse item = result.getItems().get(0);
        assertEquals("lapte zuzu", item.getProductName());
        assertEquals("Lidl", item.getStoreName());
        assertEquals(19.80, item.getPrice());
        assertEquals(2, item.getQuantity());
        assertEquals(19.80, result.getTotal(), 0.01); // delta pentru double
    }

    @Test
    void shouldReturnEmptyIfNoMatchFound() {
        when(csvService.readProductCsv(anyString(), anyString(), any())).thenReturn(Collections.emptyList());

        DailyBasketFullResponse result = dailyBasketService.optimizeBasket(
                List.of(new DailyBasketItemRequest("neexistent", 1))
        );

        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
        assertEquals(0.0, result.getTotal());
    }
}
