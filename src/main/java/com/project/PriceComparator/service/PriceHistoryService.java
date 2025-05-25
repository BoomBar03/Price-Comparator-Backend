package com.project.PriceComparator.service;

import com.project.PriceComparator.dto.PriceHistoryResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PriceHistoryService {

    public List<PriceHistoryResponse> getPriceHistory(String store, String category, String brand) {
        List<PriceHistoryResponse> history = new ArrayList<>();
        String folderPath = "data/";

        try {
            File folder = new ClassPathResource(folderPath).getFile();
            File[] files = folder.listFiles((dir, name) -> name.matches(".*_\\d{4}-\\d{2}-\\d{2}\\.csv") && !name.contains("discounts"));

            if (files == null) return history;

            for (File file : files) {
                String fileName = file.getName();
                String[] parts = fileName.replace(".csv", "").split("_");
                String storeName = parts[0];
                LocalDate date = LocalDate.parse(parts[1]);

                if (store != null && !storeName.equalsIgnoreCase(store)) continue;

                BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(folderPath + fileName).getInputStream()));
                String line = reader.readLine(); // skip header

                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(";");

                    String productId = values[0].trim();
                    String productName = values[1].trim();
                    String productCategory = values[2].trim();
                    String productBrand = values[3].trim();
                    double price = Double.parseDouble(values[6].trim());

                    if (category != null && !productCategory.equalsIgnoreCase(category)) continue;
                    if (brand != null && !productBrand.equalsIgnoreCase(brand)) continue;

                    history.add(new PriceHistoryResponse(
                            productId,
                            productName,
                            storeName,
                            productBrand,
                            productCategory,
                            price,
                            date
                    ));
                }

                reader.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return history;
    }
}
