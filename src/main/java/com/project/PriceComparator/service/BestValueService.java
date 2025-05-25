package com.project.PriceComparator.service;

import com.project.PriceComparator.dto.BestValueResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class BestValueService {

    public List<BestValueResponse> getBestValueProducts(String category, String productName, String brand) {
        List<BestValueResponse> results = new ArrayList<>();
        String folderPath = "data/";

        try {
            File folder = new ClassPathResource(folderPath).getFile();
            File[] files = folder.listFiles((dir, name) -> name.matches(".*_\\d{4}-\\d{2}-\\d{2}\\.csv") && !name.contains("discounts"));

            if (files == null) return results;

            for (File file : files) {
                String fileName = file.getName();
                String[] parts = fileName.replace(".csv", "").split("_");
                String storeName = parts[0];

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new ClassPathResource(folderPath + fileName).getInputStream()));

                String line = reader.readLine(); // skip header
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(";");

                    String id = values[0].trim();
                    String name = values[1].trim();
                    String cat = values[2].trim();
                    String prodBrand = values[3].trim();
                    double quantity = Double.parseDouble(values[4].trim());
                    String unit = values[5].trim();
                    double price = Double.parseDouble(values[6].trim());

                    if (quantity <= 0) continue;

                    if (category != null && !cat.equalsIgnoreCase(category)) continue;
                    if (productName != null && !name.toLowerCase().contains(productName.toLowerCase())) continue;
                    if (brand != null && !prodBrand.equalsIgnoreCase(brand)) continue;

                    double valuePerUnit = price / quantity;

                    results.add(new BestValueResponse(
                            id,
                            name,
                            storeName,
                            prodBrand,
                            cat,
                            price,
                            quantity,
                            unit,
                            valuePerUnit
                    ));
                }

                reader.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        results.sort(Comparator.comparingDouble(BestValueResponse::getValuePerUnit));
        return results;
    }
}
